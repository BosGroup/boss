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
import com.imooc.bos.service.system.PermissionService;
import com.imooc.bos.web.action.CommonAction;

import net.sf.json.JsonConfig;

/**  
 * ClassName:PermissionAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午2:52:43 <br/>       
 */

@Namespace("/") 
@ParentPackage("struts-default") 
@Scope("prototype")
@Controller 
public class PermissionAction extends CommonAction<Permission>{
    
    public PermissionAction() {
        super(Permission.class);
    }
    
    @Autowired
    private PermissionService permissionService;
    
    
    //#################### 权限分页查询 #######################
    @Action(value="permissionAction_pageQuery")
    public String pageQuery() throws IOException{
        
        //EasyUI的页码是从1开始的,SPringDataJPA的页码是从0开始的,所以要-1
        Pageable pageable = new PageRequest(page - 1, rows);
        Page<Permission> page = permissionService.findAll(pageable);
        
        //增加忽略属性
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"roles"});
        page2json(page, jsonConfig);
        
        return NONE;
    }
        
    
    //#################### 保存权限 #######################
    @Action(value = "permissonAction_save", results = {@Result(name = "success",
            location = "/pages/system/permission.html", type = "redirect")})
    public String save() {

        permissionService.save(getModel());
        return SUCCESS;
    }
    
    
    //#################### 查询所有权限 #######################
    @Action(value="permissionAction_findAll")
    public String findAll() throws IOException{
        
        //findAll(pageable)查询当前页,fundAll(null)查询所有
        Page<Permission> page = permissionService.findAll(null);
        List<Permission> list = page.getContent();
        
        //增加忽略属性
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"roles"});
        list2json(list, jsonConfig);
        
        return NONE;
    }
}
  
