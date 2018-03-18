package com.imooc.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.imooc.bos.domain.base.SubArea;

/**  
 * ClassName:SubAreaService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月16日 下午7:12:52 <br/>       
 */
public interface SubAreaService {

    /**  
     * 保存分区
     * @param model  
     */
    void save(SubArea model);

    /**  
     * 分页查询分区
     * @param pageable
     * @return  
     */
    Page<SubArea> findAll(Pageable pageable);

}
  
