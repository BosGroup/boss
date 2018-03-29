package com.imooc.bos.service.system;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.imooc.bos.domain.system.Role;

/**  
 * ClassName:RoleService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午4:09:17 <br/>       
 */
public interface RoleService {

    /**  
     * 角色分页查询
     * @param pageable
     * @return  
     */
    Page<Role> findAll(Pageable pageable);

    /**  
     * 保存角色
     * @param model
     * @param menuIds
     * @param permissionIds  
     */
    void save(Role model, String menuIds, Long[] permissionIds);

}
  
