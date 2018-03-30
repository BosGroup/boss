package com.imooc.bos.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.bos.dao.system.MenuRepository;
import com.imooc.bos.domain.system.Menu;
import com.imooc.bos.domain.system.User;
import com.imooc.bos.service.system.MenuService;

/**  
 * ClassName:MenuServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午8:43:21 <br/>       
 */

@Transactional
@Service
public class MenuServiceImpl implements MenuService{
    
    @Autowired
    private MenuRepository menuRepository;
    
    @Override
    public List<Menu> findLevelOne() {
        return menuRepository.findByParentMenuIsNull();
    }

    @Override
    public void save(Menu menu) {
        //需要判断用户是要添加一级菜单,若是一级菜单则parentMenu字段是一个无id的瞬时态的对象,需将其设为null
        //id类型为Long是对象类型,用null比较而不是与0比较,基本类型有默认值,而对象类型没有
        if(menu.getParentMenu() != null && menu.getParentMenu().getId() == null){
            menu.setParentMenu(null);
        }
        menuRepository.save(menu);
    }

    @Override
    public Page<Menu> findAll(Pageable pageable) {
        return menuRepository.findAll(pageable);
    }

    @Override
    public List<Menu> findbyUser(User user) {
        //如果是管理员用户,则查询所有
        if("admin".equals(user.getUsername())){
           return menuRepository.findAll();
        }
        return menuRepository.findbyUser(user.getId());
    }

}
  
