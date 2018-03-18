package com.imooc.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.imooc.bos.domain.base.FixedArea;

/**  
 * ClassName:FixedAreaService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午9:56:46 <br/>       
 */
public interface FixedAreaService {

    /**  
     * 保存定区
     * @param model  
     */
    void save(FixedArea model);

    /**  
     * 分页查询所有定区
     * @param pageable
     * @return  
     */
    Page<FixedArea> findAll(Pageable pageable);

}
  