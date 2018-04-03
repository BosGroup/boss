package com.imooc.bos.service.system.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.bos.dao.system.UserRepository;
import com.imooc.bos.domain.system.Role;
import com.imooc.bos.domain.system.User;
import com.imooc.bos.service.system.UserService;

/**  
 * ClassName:UserServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午10:11:05 <br/>       
 */

@Transactional
@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public void save(User user, Long[] roleIds) {
        userRepository.save(user);
        
        //使用脱管态对象效率更高
        if(roleIds != null && roleIds.length >0){
            for (Long roleId : roleIds) {
                Role role = new Role();
                role.setId(roleId);
                user.getRoles().add(role);
            }
        }
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public void batchDel(String ids) {
        // 真实开发中只有逻辑删除
        // 判断数据是否为空  null " "
        if(StringUtils.isNotEmpty(ids)){
            // 切割数据
            String[] split = ids.split(",");
            for (String id : split) {
                userRepository.updateDelTagById(Long.parseLong(id));
            }
        }
    }
}
  
