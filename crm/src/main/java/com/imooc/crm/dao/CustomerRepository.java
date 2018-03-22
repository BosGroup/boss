package com.imooc.crm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.imooc.crm.domain.Customer;

/**  
 * ClassName:CustomerRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月19日 下午3:44:10 <br/>       
 */

public interface CustomerRepository extends JpaRepository<Customer, Long>{
    
    //查询未关联定区的客户
    List<Customer> findByFixedAreaIdIsNull();

    //查询已关联定区的客户
    List<Customer> findByFixedAreaId(String fixedAreaId);

    //把关联到指定定区的客户进行解绑操作
    @Modifying
    @Query("update Customer set fixedAreaId = null where fixedAreaId = ?")
    void unbindCustomerByFixedArea(String fixedAreaId);
    
    
    //把客户绑定到指定的定区
    @Modifying
    @Query("update Customer set fixedAreaId = ?1 where id = ?2")
    void bindCustomerByFixedArea(String fixedAreaId, Long customerId);
    
    
    //激活用户
    @Modifying
    @Query("update Customer set type = 1 where telephone =?")
    void active(String telephone);
    
    //判断用户是否激活
    Customer findByTelephone(String telephone);
    
    //用户登录
    Customer findByTelephoneAndPassword(String telephone, String password);

}
  
