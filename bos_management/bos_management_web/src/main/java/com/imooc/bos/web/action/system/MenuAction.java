package com.imooc.bos.web.action.system;

import java.io.IOException;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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

import com.imooc.bos.domain.system.Menu;
import com.imooc.bos.domain.system.User;
import com.imooc.bos.service.system.MenuService;
import com.imooc.bos.web.action.CommonAction;

import net.sf.json.JsonConfig;


/**  
 * ClassName:MenuAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午8:30:00 <br/>       
 */

@Namespace("/") 
@ParentPackage("struts-default") 
@Scope("prototype") 
@Controller 
public class MenuAction extends CommonAction<Menu>{
    
    public MenuAction() {
        super(Menu.class);
    }
    
    @Autowired
    private MenuService menuService;
    
    
    //###################### 查询菜单 ###############################
    @Action(value = "menuAction_findLevelOne")
    public String findLevelOne() throws IOException{
        //查询所有的一级菜单,二级菜单是通过一级菜单的children获取出来的
       List<Menu> list = menuService.findLevelOne();
       
       //增加忽略的属性,忽略roles,childrenMenus是避免懒加载异常,忽略parentMenu是避免死循环(子菜单查询父菜单, 父菜单又去查询子菜单)
       JsonConfig jsonConfig = new JsonConfig();
       jsonConfig.setExcludes(new String[]{ "roles", "childrenMenus", "parentMenu" });
       
       list2json(list, jsonConfig);
       return NONE;
    }
    
    //###################### 保存菜单 ################################
    @Action(value="menuAction_save",results={@Result(name="success",location="/pages/system/menu.html",type="redirect")})
    public String save() {
        menuService.save(getModel());
        return SUCCESS;
    }
    
    //###################### 菜单分页查询 ################################
    @Action(value = "menuAction_pageQuery")
    public String pageQuery() throws IOException {

        //EasyUI的页码是从1开始的,SPringDataJPA的页码是从0开始的,所以要-1
        //Menu类中也有page属性,struts框架在封装数据的时候优先封装给模型对象,导致属性驱动接收不到page,此处应从模型驱动中取值
        Pageable pageable = new PageRequest(Integer.parseInt(getModel().getPage()) - 1, rows);
        Page<Menu> page = menuService.findAll(pageable);
        
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"roles", "childrenMenus", "parentMenu"});
        page2json(page, jsonConfig);
        
        return NONE;
    }
    
    //###################### 根据用户查询菜单 ################################
    @Action(value="menuAction_findbyUser")
    public String findbyUser() throws IOException{
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        
        List<Menu> list = menuService.findbyUser(user);
        
        
        //增加忽略的属性,忽略roles,childrenMenus是避免懒加载异常,忽略parentMenu是避免死循环
        //忽略children属性是放弃构造标准json的ztree,而使用简单json的ztree,避免数据重复
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"roles", "childrenMenus", "parentMenu","children"});
        list2json(list, jsonConfig);
        
        return NONE;
    }
}
  
