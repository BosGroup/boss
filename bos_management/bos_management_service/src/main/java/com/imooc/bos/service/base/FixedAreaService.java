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

    /**  
     * 关联快递员
     * @param id
     * @param courierId
     * @param takeTimeId  
     */
    void associationCourierToFixedArea(Long fixedAreaId, Long courierId, Long takeTimeId);

    /**  
     * 定区关联分区
     * @param id
     * @param subAreaIds  
     */
    void assignsubAreas2FixedArea(Long fixedAreaId, Long[] subAreaIds);

    /**
     * 批量删除定区 
     * @param ids
     */
    void batchDel(String ids);

}
  
