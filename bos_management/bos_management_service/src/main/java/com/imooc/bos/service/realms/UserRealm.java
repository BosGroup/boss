package com.imooc.bos.service.realms;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.imooc.bos.dao.system.PermissionRepository;
import com.imooc.bos.dao.system.RoleRepository;
import com.imooc.bos.dao.system.UserRepository;
import com.imooc.bos.domain.system.Permission;
import com.imooc.bos.domain.system.Role;
import com.imooc.bos.domain.system.User;

/**  
 * ClassName:UserRealm <br/>  
 * Function:  <br/>  
 * Date:     2018年3月27日 下午9:10:44 <br/>       
 */

@Component  //交个spring管理,在安全管理器中注入realm
public class UserRealm extends AuthorizingRealm{
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PermissionRepository permissionRepository;
    
    /**
     * 授权的方法
     * 每一次访问需要权限的资源的时候,都会调用授权的方法
     * 缺点:影响访问速度,可以用缓存解决,将权限和角色存入缓存,访问需要权限的资源的时候不会再次调用授权的方法
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
         
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //授权和角色
        /*info.addStringPermission("courierAction_pageQuery");
        info.addRole("admin");*/
        
        //需要根据当前的用户去查询对应的权限和角色
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        if("admin".equals(user.getUsername())){
            //内置管理员的权限和角色是写死的
            //角色其实是权限的集合,并不是所有的权限都会包含在某一个角色中
            List<Role> roles = roleRepository.findAll();
            for (Role role : roles) {
                info.addRole(role.getKeyword());
            }
            
            List<Permission> permissions = permissionRepository.findAll();
            for (Permission permission : permissions) {
                info.addStringPermission(permission.getKeyword());
            }
        }else{
            //此处不能通过命名规范查询,by应该小写
            List<Role> roles = roleRepository.findbyUid(user.getId());
            for (Role role : roles) {
                info.addRole(role.getKeyword());
            }
            
            List<Permission> permissions = permissionRepository.findbyUid(user.getId());
            for (Permission permission : permissions) {
               info.addStringPermission(permission.getKeyword()); 
            }
        }
        
        return info;
    }
    
    /**
     * 认证的方法,用于用户登录认证
     * 参数中的token就是subject.login(token)方法中传入的参数
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        //强转令牌,获取用户名
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String username = usernamePasswordToken.getUsername();
        //根据用户名查找用户
        User user = userRepository.findByUsername(username);
        if(user != null){
            /**
             * 找到,比对密码
             * @param principal 当事人,主体.通常是从数据库中查询到的用户
             * @param credentials 凭证,密码.是从数据库中查询出来的密码
             * @param realmName
             */
            AuthenticationInfo info = new SimpleAuthenticationInfo(user,user.getPassword(), getName());
            //比对成功 ->执行后续的逻辑
            //比对失败 ->抛异常
            return info;
        }
        //找不到 ->抛异常
        return null;
    }
}
  
