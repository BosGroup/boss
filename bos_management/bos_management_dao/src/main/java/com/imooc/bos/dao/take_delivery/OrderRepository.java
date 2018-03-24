package com.imooc.bos.dao.take_delivery;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imooc.bos.domain.take_delivery.Order;

/**  
 * ClassName:OrderRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月23日 下午4:48:53 <br/>       
 */
public interface OrderRepository extends JpaRepository<Order, Long>{

}
  
