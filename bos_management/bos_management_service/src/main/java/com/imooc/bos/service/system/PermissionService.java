package com.imooc.bos.service.system;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.imooc.bos.domain.system.Permission;

/**  
 * ClassName:PermissionService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午2:54:41 <br/>       
 */
public interface PermissionService {

    /**  
     * 分页查询权限
     * @param pageable
     * @return  
     */
    Page<Permission> findAll(Pageable pageable);
    
    /**  
     * 保存权限
     * @param permission  
     */
    void save(Permission permission);


}
  
