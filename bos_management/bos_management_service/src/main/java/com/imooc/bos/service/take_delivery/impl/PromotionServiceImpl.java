package com.imooc.bos.service.take_delivery.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.bos.dao.take_delivery.PromotionRepository;
import com.imooc.bos.domain.take_delivery.Promotion;
import com.imooc.bos.service.take_delivery.PromotionService;

/**  
 * ClassName:PromotionServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月31日 下午4:00:19 <br/>       
 */

@Transactional
@Service
public class PromotionServiceImpl implements PromotionService {
    
    @Autowired
    private PromotionRepository promotionRepository;

    @Override
    public void save(Promotion promotion) {
        promotionRepository.save(promotion);
    }

    @Override
    public Page<Promotion> findAll(Pageable pageable) {
        return promotionRepository.findAll(pageable);
    }
    
}
  
