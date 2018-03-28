package com.imooc.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imooc.bos.domain.system.Menu;

/**  
 * ClassName:MenuRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午8:45:57 <br/>       
 */
public interface MenuRepository extends JpaRepository<Menu, Long>{
    
    //查找菜单的一级节点
    List<Menu> findByParentMenuIsNull();

}
  
