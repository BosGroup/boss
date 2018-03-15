package com.imooc.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.imooc.bos.domain.base.Area;

/**  
 * ClassName:AreaService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午5:34:59 <br/>       
 */
public interface AreaService {

    /**  
     * 批量保存区域
     * @param list  
     */
    void save(List<Area> list);

    /**  
     * 分页查询区域
     * @param pageable
     * @return  
     */
    Page<Area> findAll(Pageable pageable);



}
  
