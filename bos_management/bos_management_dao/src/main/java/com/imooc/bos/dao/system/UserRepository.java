package com.imooc.bos.dao.system;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imooc.bos.domain.system.User;

/**  
 * ClassName:UserRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月27日 下午9:13:15 <br/>       
 */
public interface UserRepository extends JpaRepository<User,Long>{
    
    User findByUsername(String username);
}
  
