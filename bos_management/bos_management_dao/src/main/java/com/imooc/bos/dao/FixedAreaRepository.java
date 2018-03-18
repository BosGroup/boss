package com.imooc.bos.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imooc.bos.domain.base.FixedArea;

/**  
 * ClassName:FixedAreaRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午10:06:32 <br/>       
 */

//泛型1 : 封装数据的对象的类型
//泛型2 : 对象的主键的类型
public interface FixedAreaRepository extends JpaRepository<FixedArea, Long>{

}
  
