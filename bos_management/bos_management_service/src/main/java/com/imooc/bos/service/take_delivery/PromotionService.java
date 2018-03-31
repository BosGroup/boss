package com.imooc.bos.service.take_delivery;


import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.imooc.bos.domain.take_delivery.Promotion;

/**  
 * ClassName:PromotionService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月31日 下午3:59:23 <br/>       
 */

public interface PromotionService {

    /**  
     * 保存宣传任务
     * @param promotion  
     */
    void save(Promotion promotion);

    /**  
     * 宣传任务分页查询
     * @param pageable
     * @return  
     */
    Page<Promotion> findAll(Pageable pageable);
    
    
   
    
    
}
  
