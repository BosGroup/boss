package com.imooc.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.bos.dao.StandardRepository;
import com.imooc.bos.domain.base.Standard;
import com.imooc.bos.service.base.StandardService;

/**  
 * ClassName:StandardServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午3:26:07 <br/>       
 */

@Transactional
@Service
public class StandardServiceImpl implements StandardService{
    
    @Autowired
    private StandardRepository standardRepository;
    
    @Override
    public void save(Standard standard) {
         standardRepository.save(standard); 
    }

    @Override
    public Page<Standard> findAll(Pageable pageable) {
      
        return standardRepository.findAll(pageable);
    }

}
  
