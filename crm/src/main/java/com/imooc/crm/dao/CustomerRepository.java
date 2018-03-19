package com.imooc.crm.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imooc.crm.domain.Customer;

/**  
 * ClassName:CustomerRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月19日 下午3:44:10 <br/>       
 */

public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
  
