package com.imooc.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.imooc.bos.domain.system.Menu;

/**  
 * ClassName:MenuRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午8:45:57 <br/>       
 */
public interface MenuRepository extends JpaRepository<Menu, Long>{
    
    //查找菜单的一级节点
    List<Menu> findByParentMenuIsNull();
    
    @Query("select m from Menu m inner join m.roles r inner join r.users u where u.id = ?")
    List<Menu> findbyUser(Long id);

}
  
