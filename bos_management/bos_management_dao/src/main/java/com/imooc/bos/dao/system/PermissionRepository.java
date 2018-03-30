package com.imooc.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.imooc.bos.domain.system.Permission;

/**  
 * ClassName:PermissionRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午3:07:22 <br/>       
 */

public interface PermissionRepository extends JpaRepository<Permission, Long>{
    
    // SELECT * FROM T_PERMISSION p
    // INNER JOIN T_ROLE_PERMISSION rp ON rp.C_PERMISSION_ID = p.C_ID
    // INNER JOIN T_ROLE r ON r.C_ID = rp.C_ROLE_ID
    // INNER JOIN T_USER_ROLE ur ON ur.C_ROLE_ID = r.C_ID
    // INNER JOIN T_USER u ON u.C_ID = ur.C_USER_ID
    // WHERE u.C_ID = ?
    
    @Query("select p from Permission p inner join p.roles r inner join r.users u where u.id = ?")
    List<Permission> findbyUid(Long uid);
    
}
  
