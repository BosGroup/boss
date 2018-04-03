package com.imooc.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.bos.dao.TakeTimeRepository;
import com.imooc.bos.domain.base.TakeTime;
import com.imooc.bos.service.base.TakeTimeService;

/**  
 * ClassName:TakeTimeServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月21日 下午4:13:56 <br/>       
 */
@Service
@Transactional
public class TakeTimeServiceImpl implements TakeTimeService {

    @Autowired
    private TakeTimeRepository takeTimeRepository;


    @Override
    public void save(TakeTime takeTime) {
        takeTimeRepository.save(takeTime);
    }


    @Override
    public Page<TakeTime> findAll(Pageable pageable) {
        return takeTimeRepository.findAll(pageable);
    }


    @Override
    public List<TakeTime> findAll() {
        return takeTimeRepository.findAll();
    }
}
  
