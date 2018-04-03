package com.imooc.bos.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imooc.bos.domain.base.Vehicle;

/**  
 * ClassName:VehicleRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年4月3日 上午11:27:49 <br/>       
 */
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    
}
  
