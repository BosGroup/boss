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
            MailUtils.sendMail("激活邮件",emailBody,model.getEmail());
            
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
    
    
    //################## 用户登录 #########################
    @Action(value="customerAction_login",results = {@Result(name = "success", location = "/index.html",type = "redirect"),
            @Result(name = "error", location = "/login.html",type = "redirect"),
            @Result(name = "unactived", location = "/login.html",type = "redirect")})
    public String login(){
        //获取登录校验码
        String serverCode = (String) ServletActionContext.getRequest().getSession().getAttribute("validateCode");
        //验证校验码checkcode已被属性驱动获取
        if(StringUtils.isNotEmpty(serverCode) && StringUtils.isNotEmpty(checkcode)
                && serverCode.equals(checkcode)){
            //校验用户是否激活
            Customer customer = WebClient.create("http://localhost:8180/crm/webService/customerService/isActived")
            .type(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .query("telephone", model.getTelephone())  //参数
            .get(Customer.class);     //返回值
            
            //空指针异常customer.getType()的返回值为Integer类型,为对象类型,注意区别int和Integer
            if(customer != null && customer.getType() != null){
                if(customer.getType() == 1){
                    // 激活了,登录
                    Customer c = WebClient.create("http://localhost:8180/crm/webService/customerService/login")
                            .type(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .query("telephone", model.getTelephone())
                            .query("password", model.getPassword())
                            .get(Customer.class);
                    if(c != null){
                        //将用户存储到域对象
                        ServletActionContext.getRequest().getSession().setAttribute("user", c);
                        return SUCCESS;
                    }else{
                        return ERROR;
                    }
                }else{
                    //用户已经注册成功，但是没有激活    重新发送邮件激活???
                    return "unactived";
                }
            }
        }
        return ERROR;
    }
}
  
