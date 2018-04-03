package com.imooc.bos.service.take_delivery.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.bos.dao.take_delivery.WaybillRepository;
import com.imooc.bos.domain.take_delivery.WayBill;
import com.imooc.bos.service.take_delivery.WaybillService;

/**  
 * ClassName:WaybillServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月25日 下午9:23:13 <br/>       
 */

@Transactional
@Service
public class WaybillServiceImpl implements WaybillService {
    
    @Autowired
    private WaybillRepository waybillRepository;

    @Override
    public void batchImport(List<WayBill> list) {
          
        waybillRepository.save(list);
        
    }
    
    @Override
    public void save(WayBill wayBill) {
        waybillRepository.save(wayBill);
    }

    
   
}
  
