package com.imooc.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.imooc.bos.domain.base.Courier;

/**  
 * ClassName:CourierService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午7:52:15 <br/>       
 */
public interface CourierService {

    /**  
     * 保存快递员信息
     * @param model  
     */
    void save(Courier model);

    /**  
     * 分页查询快递员信息
     * @param pageable
     * @return  
     */
    Page<Courier> findAll(Pageable pageable);

    /**  
     * 批量删除快递员信息
     * @param ids  
     */
    void batchDel(String ids);

}
  
