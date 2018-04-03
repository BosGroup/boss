package com.imooc.bos.dao.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.imooc.bos.domain.system.User;

/**  
 * ClassName:UserRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月27日 下午9:13:15 <br/>       
 */
public interface UserRepository extends JpaRepository<User,Long>{
    
    User findByUsername(String username);
    
    // 自定义方法
    // 根据ID更改删除的标志位
    @Modifying
    @Query("update User set remark = '1' where id = ?")
    void updateDelTagById(long parseLong);

}
  
