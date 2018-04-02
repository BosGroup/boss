package com.imooc.bos.service.base;

import java.util.List;

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

    /**  
     * 查询未关联到定区的分区
     * @return  
     */
    List<SubArea> findUnAssociatedsubAreas();

    /**  
     * 查询已关联到指定定区的分区
     * @param id
     * @return  
     */
    List<SubArea> findAssociatedsubAreas(Long fixedAreaId);


     //导出图表
    List<Object[]> exportCharts();
   

}
  
