package com.imooc.bos.domain.system;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @description:菜单
 */
@Entity
@Table(name = "T_MENU")
public class Menu implements Serializable{
    @Id
    @GeneratedValue
    @Column(name = "C_ID")
    private Long id;
    
    @Column(name = "C_NAME")
    private String name; // 菜单名称
    
    @Column(name = "C_PAGE")
    private String page; // 访问路径
    
    @Column(name = "C_PRIORITY")
    private Integer priority; // 优先级
    
    @Column(name = "C_DESCRIPTION")
    private String description; // 描述

    @ManyToMany(mappedBy = "menus")
    private Set<Role> roles = new HashSet<Role>(0);
    
    //自关联:自己和自己有关系,此处代表一个菜单有多个子菜单,fetch立即查询数据避免懒加载
    @OneToMany(mappedBy = "parentMenu",fetch = FetchType.EAGER)
    private Set<Menu> childrenMenus = new HashSet<Menu>();

    @ManyToOne
    @JoinColumn(name = "C_PID")
    private Menu parentMenu;
    
    
    //新增get方法,返回标准json数据时提供combotree需要的text和children字段
    //父节点会查询childrenMenus,所有只需查询一级菜单,就可以查询到所有的菜单
    public String getText() {
        return name;
    }
    public Set<Menu> getChildren(){
        //此处返回的childMenus是集合属性,会发生懒加载异常,需在childrenMenus增加属性,把需要的数据立即查询出来
        return childrenMenus;
    }
    
    //新增get方法,返回简单json数据时需提供的pId字段
    public Long getpId(){
        if(parentMenu == null){
            return 0L;
        }
        return parentMenu.getId();
    }
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Menu> getChildrenMenus() {
        return childrenMenus;
    }

    public void setChildrenMenus(Set<Menu> childrenMenus) {
        this.childrenMenus = childrenMenus;
    }

    public Menu getParentMenu() {
        return parentMenu;
    }

    public void setParentMenu(Menu parentMenu) {
        this.parentMenu = parentMenu;
    }

}
