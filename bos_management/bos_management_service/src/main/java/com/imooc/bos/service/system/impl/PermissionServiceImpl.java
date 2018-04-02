package com.imooc.bos.service.system.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.bos.dao.system.PermissionRepository;
import com.imooc.bos.dao.system.RoleRepository;
import com.imooc.bos.domain.system.Permission;
import com.imooc.bos.domain.system.Role;
import com.imooc.bos.service.system.PermissionService;

/**  
 * ClassName:PermissionServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午3:04:13 <br/>       
 */

@Transactional
@Service
public class PermissionServiceImpl implements PermissionService{
    
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private RoleRepository roleRepository;
    
    @Override
    public Page<Permission> findAll(Pageable pageable) {
        return permissionRepository.findAll(pageable);
    }


    @Override
    public void save(Permission permission) {
        permissionRepository.save(permission);
    }


    @Override
    public List<Permission> findByRoleId(Long roleId) {
          
        Role role = roleRepository.findOne(roleId);
        Set<Permission> permissions = role.getPermissions();
        ArrayList<Permission> list = new ArrayList<>(permissions);
        return list;
    }

}
  
