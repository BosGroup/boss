package com.imooc.bos.service.system;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.imooc.bos.domain.system.Role;
import com.imooc.bos.domain.system.User;

/**  
 * ClassName:UserService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午10:10:28 <br/>       
 */
public interface UserService {

    /**  
     * 保存用户
     * @param model
     * @param roleIds  
     */
    void save(User user, Long[] roleIds);

    /**  
     * 用户分页查询
     * @param pageable
     * @return  
     */
    Page<User> findAll(Pageable pageable);

    void batchDel(String ids);

}
  
