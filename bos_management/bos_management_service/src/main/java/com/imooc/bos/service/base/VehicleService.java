package com.imooc.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.imooc.bos.domain.base.Vehicle;

/**  
 * ClassName:VehicleService <br/>  
 * Function:  <br/>  
 * Date:     2018年4月3日 上午11:28:45 <br/>       
 */
public interface VehicleService {

    void save(Vehicle vehicle);

    Page<Vehicle> findAll(Pageable pageable);

    void delVehicle(Long vehicleId);



}
  
