package com.imooc.bos.dao.system;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imooc.bos.domain.system.Permission;

/**  
 * ClassName:PermissionRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午3:07:22 <br/>       
 */

public interface PermissionRepository extends JpaRepository<Permission, Long>{
    
}
  
