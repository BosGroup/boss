package com.imooc.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.bos.dao.TakeTimeRepository;
import com.imooc.bos.domain.base.TakeTime;
import com.imooc.bos.service.base.TakeTimeService;

/**  
 * ClassName:TakeTimeServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月20日 下午11:25:50 <br/>       
 */

@Transactional
@Service
public class TakeTimeServiceImpl implements TakeTimeService{
    
    @Autowired
    private TakeTimeRepository takeTimeRepository;

    @Override
    public List<TakeTime> findAll() {
        return takeTimeRepository.findAll();
    }
}
  
