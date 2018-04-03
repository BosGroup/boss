package com.imooc.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.imooc.bos.domain.base.TakeTime;

/**  
 * ClassName:TakeTimeService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月21日 下午4:13:38 <br/>       
 */
public interface TakeTimeService {

    void save(TakeTime takeTime);

    Page<TakeTime> findAll(Pageable pageable);

    List<TakeTime> findAll();

    void batchDel(String ids);

}
  
