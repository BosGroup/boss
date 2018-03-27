package com.imooc.bos.web.action.system;


import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.imooc.bos.domain.system.User;
import com.imooc.bos.web.action.CommonAction;

import freemarker.template.utility.SecurityUtilities;


/**  
 * ClassName:UserAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月27日 下午7:29:15 <br/>       
 */

@Namespace("/") 
@ParentPackage("struts-default") 
@Scope("prototype")
@Controller 
public class UserAction extends CommonAction<User>{
    
    public UserAction() {
        super(User.class);
    }
    
    
    //#################### 用户登录 #######################
    //属性驱动获取用户输入的验证码
    private String checkcode;
    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }
    
    
    @Action(value = "userAction_login",
            results = {@Result(name = "success", location = "/index.html",type = "redirect"),
                    @Result(name = "login", location = "/login.html",type = "redirect")})
    public String login(){
        //获取系统生成的验证码
        String serverCode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
        //验证验证码
        if(StringUtils.isNotEmpty(serverCode) && StringUtils.isNotEmpty(checkcode)
                && serverCode.equals(checkcode)){
            
            //主体,代表当前用户
            Subject subject = SecurityUtils.getSubject();
            //创建用户名密码令牌
            AuthenticationToken token = new UsernamePasswordToken(
                    getModel().getUsername(),getModel().getPassword());
            try {
                //执行登录,跳转到认证的方法
                subject.login(token);
                
                //登录成功后,获取User
                //方法的返回值由Realm中doGetAuthenticationInfo方法定义SimpleAuthenticationInfo对象的时候,第一个参数决定的
                User user = (User) subject.getPrincipal();
                
                //将user存入域对象
                ServletActionContext.getRequest().getSession().setAttribute("user", user);
                return SUCCESS;
            } catch (UnknownAccountException e) {
                e.printStackTrace();
                System.out.println("用户名写错了");
            } catch (IncorrectCredentialsException e) {
                e.printStackTrace();
                System.out.println("密码错误");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("其他错误");
            }
        }
        return LOGIN;
    }
    
    
    
    
    
    
    
    
}
  
