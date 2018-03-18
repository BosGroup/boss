package com.imooc.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.bos.dao.FixedAreaRepository;
import com.imooc.bos.domain.base.FixedArea;
import com.imooc.bos.service.base.FixedAreaService;

/**  
 * ClassName:FixedAreaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午10:03:58 <br/>       
 */

@Transactional
@Service       //spring的注解,业务层代码
public class FixedAreaServiceImpl implements FixedAreaService{
    
    @Autowired
    private FixedAreaRepository fixedAreaRepository;
    
    @Override
    public void save(FixedArea fixedArea) {
         fixedAreaRepository.save(fixedArea);
    }

    @Override
    public Page<FixedArea> findAll(Pageable pageable) {
        return fixedAreaRepository.findAll(pageable);
    }

}
  
