package com.imooc.bos.service.system;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.imooc.bos.domain.system.Menu;

/**  
 * ClassName:MenuService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午8:38:22 <br/>       
 */
public interface MenuService {

    /**  
     * 查找菜单的一级节点
     * @return  
     */
    List<Menu> findLevelOne();

    /**  
     * 保存菜单
     * @param model  
     */
    void save(Menu model);

    /**  
     * 分页查询菜单
     */
    Page<Menu> findAll(Pageable pageable);

}
  
