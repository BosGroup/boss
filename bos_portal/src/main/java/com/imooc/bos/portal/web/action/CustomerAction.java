package com.imooc.bos.portal.web.action;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.aliyuncs.exceptions.ClientException;
import com.imooc.bos.bosUtils.SmsUtils;
import com.imooc.bos.crm.domain.Customer;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**  
 * ClassName:CustomerAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月21日 下午8:48:27 <br/>       
 */
public class CustomerAction extends ActionSupport implements ModelDriven<Customer>{
    
    private Customer model = new Customer();
    
    @Override
    public Customer getModel() {
        return model;
    }
    
    
    //################## 注册页面发送验证码 #########################
    @Action(value="customerAction_sendSMS")
    public String sendSMS(){
        //随机生成验证码
        String code = RandomStringUtils.randomNumeric(6);
        System.out.println(code);
        //储存验证码
        ServletActionContext.getRequest().getSession().setAttribute("serverCode", code);
        //发送验证码
        try {
            //取出模型驱动封装前端传递的手机号
            SmsUtils.sendSms(model.getTelephone(), code);
        } catch (ClientException e) {
            e.printStackTrace();  
        }
        return NONE;
    }
    
    
    //################## 用户注册 #########################
    //使用属性驱动获取用户输入的验证码
    private String checkcode;
    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }
    
    @Action(value="customerAction_regist",
            results={@Result(name="success",location="/signup-success.html",type="redirect"),
                    @Result(name="error",location="/signup-fail.html",type="redirect")})
    public String regist(){
        //校验手机号
        //校验验证码
        String serverCode = (String) ServletActionContext.getRequest().getSession().getAttribute("serverCode");
        if(StringUtils.isNotEmpty(serverCode) && StringUtils.isNotEmpty(checkcode)
                && serverCode.equals(checkcode)){
            //注册
            WebClient.create("http://localhost:8180/crm/webService/customerService/save")
            .type(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .post(model);
            
            return SUCCESS;
        }
        
        return ERROR;
    }
    
}
  
