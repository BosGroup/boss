package com.imooc.bos.portal.web.action;

import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.aliyuncs.exceptions.ClientException;
import com.imooc.bos.bosUtils.MailUtils;
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
    
    //注入redis模板
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
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
            
            //生成激活码
            String activeCode = RandomStringUtils.randomNumeric(32);
            
            //存储激活码,使用spring-data-redis,key使用用户的手机号作为唯一标识,设置有效期为一天
            redisTemplate.opsForValue().set(model.getTelephone(), activeCode,1,TimeUnit.DAYS);            
            
            String emailBody = "感谢您注册本网站的账号,请在24小时之内点击<a href='http://localhost:8280/bos_portal/customerAction_active.action?activeCode="
                    +activeCode+"&telephone=" + model.getTelephone()+ "'>激活链接</a>激活您的帐号";
            
            //根据用户填写的邮箱地址发送激活邮件
            MailUtils.sendMail(model.getEmail(), "激活邮件", emailBody);
            
            return SUCCESS;
        }
        
        return ERROR;
    }
    
    
    //################## 用户激活 #########################
    //使用属性驱动获取激活码
    private String activeCode;
    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
    }
    
    @Action(value="customerAction_active",results={@Result(name="success",location="/login.html",type="redirect"),
        @Result(name="error",location="/signup-fail.html",type="redirect")})
    public String active(){
        //对比激活码
        String serverCode = redisTemplate.opsForValue().get(model.getTelephone());
        if(StringUtils.isNotEmpty(activeCode) && StringUtils.isNotEmpty(serverCode)
                && serverCode.equals(activeCode)){
            //激活
            WebClient.create("http://localhost:8180/crm/webService/customerService/active")
            .type(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .query("telephone", model.getTelephone())
            .put(null);
            
            return SUCCESS;
        }
        return ERROR;
    }
    
}
  
