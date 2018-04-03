package com.imooc.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.bos.dao.VehicleRepository;
import com.imooc.bos.domain.base.Vehicle;
import com.imooc.bos.service.base.VehicleService;

/**  
 * ClassName:VehicleServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年4月3日 上午11:28:58 <br/>       
 */
@Service
@Transactional
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;
    @Override
    public void save(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
    }
    @Override
    public Page<Vehicle> findAll(Pageable pageable) {
          
        return vehicleRepository.findAll(pageable);
    }
    @Override
    public void delVehicle(Long vehicleId) {
          
       vehicleRepository.delete(vehicleId);
        
    }
  

}
  
