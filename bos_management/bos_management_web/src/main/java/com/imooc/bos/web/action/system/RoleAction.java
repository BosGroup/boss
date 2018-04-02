package com.imooc.bos.web.action.system;

import java.io.IOException;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.imooc.bos.domain.system.Permission;
import com.imooc.bos.domain.system.Role;
import com.imooc.bos.service.system.RoleService;
import com.imooc.bos.web.action.CommonAction;
import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import net.sf.json.JsonConfig;

/**  
 * ClassName:RoleAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午4:11:29 <br/>       
 */

@Namespace("/")
@ParentPackage("struts-default") 
@Scope("prototype")
@Controller 
public class RoleAction extends CommonAction<Role> {
    
    public RoleAction() {
        super(Role.class);
    }
    
    @Autowired
    private RoleService roleService;
    
    
    //#################### 角色分页查询 #######################
    @Action(value="roleAction_pageQuery")
    public String pageQuery() throws IOException{
        
        //EasyUI的页码是从1开始的,SPringDataJPA的页码是从0开始的,所以要-1
        Pageable pageable = new PageRequest(page - 1, rows);
        Page<Role> page = roleService.findAll(pageable);
        
        //增加忽略属性
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"users","permissions","menus"});
        page2json(page, jsonConfig);
        
        return NONE;
    }
    
    //#################### 保存角色 #######################
    //属性驱动获取菜单和权限的ID
    private String menuIds;
    public void setMenuIds(String menuIds) {
        this.menuIds = menuIds;
    }
    private Long[] permissionIds;
    public void setPermissionIds(Long[] permissionIds) {
        this.permissionIds = permissionIds;
    }
    
    @Action(value="roleAction_save",results={@Result(name = "success",
            location = "/pages/system/role.html", type = "redirect")})
    public String save(){
        //System.out.println(getModel().getId());
        roleService.save(getModel(),menuIds,permissionIds);
        return SUCCESS;
    }
    
    
    //#################### 查询所有角色 #######################
    @Action(value="roleAction_findAll")
    public String findAll() throws IOException{
        
        Page<Role> page = roleService.findAll(null);
        List<Role> list = page.getContent();
        //增加忽略属性
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"users","permissions","menus"});
        
        list2json(list, jsonConfig);
        return NONE;
        
    }
}
  
