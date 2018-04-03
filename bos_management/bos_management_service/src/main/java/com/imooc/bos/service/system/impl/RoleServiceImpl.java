package com.imooc.bos.service.system.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ctc.wstx.util.StringUtil;
import com.imooc.bos.dao.system.MenuRepository;
import com.imooc.bos.dao.system.PermissionRepository;
import com.imooc.bos.dao.system.RoleRepository;
import com.imooc.bos.domain.system.Menu;
import com.imooc.bos.domain.system.Permission;
import com.imooc.bos.domain.system.Role;
import com.imooc.bos.service.system.RoleService;


/**  
 * ClassName:RoleServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午4:09:46 <br/>       
 */

@Transactional
@Service
public class RoleServiceImpl implements RoleService {
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private MenuRepository menuRepository;
    
    @Autowired
    private PermissionRepository permissonRepository;

    @Override
    public Page<Role> findAll(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    @Override
    public void save(Role role, String menuIds, Long[] permissionIds) {
        
        roleRepository.save(role);
        role=roleRepository.findOne(role.getId());
        
        //保存角色方法一:查询持久态对象,此种方法需要联表查询,效率较低
        /*if(StringUtils.isNotEmpty(menuIds)){
            String[] split = menuIds.split(",");
            for (String menuId : split) {
                Menu menu = menuRepository.findOne(Long.parseLong(menuId));
                role.getMenus().add(menu);
            }
        }
        if (permissionIds != null && permissionIds.length > 0) {
            for (Long permissionId : permissionIds) {
                Permission permission = permissonRepository.findOne(permissionId);
                role.getPermissions().add(permission);
            }
        }*/
        
        //保存角色方法二:使用脱管态对象,此种方法效率更高
        //保存角色实际上是向角色表中插入一条数据,向两个中间表中插入若干条数据,中间表都是id,无其他字段
        if (StringUtils.isNotEmpty(menuIds)) {
            String[] split = menuIds.split(",");
            for (String menuId : split) {
                Menu menu = new Menu();
                menu.setId(Long.parseLong(menuId));
                role.getMenus().add(menu);
            }
        }
        
        if (permissionIds != null && permissionIds.length > 0) {
            for (Long permissionId : permissionIds) {
                Permission permission = new Permission();
                permission.setId(permissionId);
                role.getPermissions().add(permission);
            }
        }
    }
}
  
