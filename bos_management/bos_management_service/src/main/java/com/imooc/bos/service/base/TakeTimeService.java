package com.imooc.bos.service.base;

import java.util.List;

import com.imooc.bos.domain.base.TakeTime;

/**  
 * ClassName:TakeTimeService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月20日 下午11:25:15 <br/>       
 */
public interface TakeTimeService {

    /**  
     * 查询所有收派时间
     * @return  
     */
    List<TakeTime> findAll();

}
  
