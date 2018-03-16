package com.imooc.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.bos.dao.SubAreaRepository;
import com.imooc.bos.domain.base.SubArea;
import com.imooc.bos.service.base.SubAreaService;

/**  
 * ClassName:SubAreaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月16日 下午7:42:52 <br/>       
 */

@Transactional
@Service
public class SubAreaServiceImpl implements SubAreaService {
    
    @Autowired
    private SubAreaRepository subAreaRepository;
    
    @Override
    public void save(SubArea model) {
        subAreaRepository.save(model);
    }

}
  
