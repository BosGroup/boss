package com.imooc.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.imooc.bos.domain.base.Standard;

/**  
 * ClassName:StandardService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午3:15:20 <br/>       
 */
public interface StandardService {
    
    /**
     * 保存取派标准
     * @param model
     */
    void save(Standard standard);
    
    /**
     * 分页查询数据
     * @param pageable
     * @return
     */
    Page<Standard> findAll(Pageable pageable);

    void batchDel(String ids);

}
  
