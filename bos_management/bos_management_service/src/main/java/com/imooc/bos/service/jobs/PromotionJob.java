package com.imooc.bos.service.jobs;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.imooc.bos.dao.take_delivery.PromotionRepository;
import com.imooc.bos.domain.take_delivery.Promotion;

@Component
public class PromotionJob {
@Autowired
private PromotionRepository promotionRepository;
public void modifyStatus(){
    List<Promotion> list =promotionRepository.findAll();
    Date thisTime=new Date();
    for (Promotion promotion : list) {
        if(promotion.getEndDate().before(thisTime)){
            promotion.setStatus("2");
        }
    }
    
}
}
