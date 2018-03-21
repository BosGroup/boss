package com.imooc.bos.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imooc.bos.domain.base.FixedArea;
import com.imooc.bos.domain.base.SubArea;

/**  
 * ClassName:SubAreaRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月16日 下午7:54:21 <br/>       
 */

public interface SubAreaRepository extends JpaRepository<SubArea, Long> {
    
    //查询未关联到定区的分区
    List<SubArea> findByFixedAreaIsNull();
    
    //查询已关联到指定定区的分区
    //使用SpringDataJPA的命名规范进行查询的时候，
    //如果字段是对象，必须是单一对象，不能是集合,传入的参数必须指定id属性
    List<SubArea> findByFixedArea(FixedArea fixedArea);

}
  
