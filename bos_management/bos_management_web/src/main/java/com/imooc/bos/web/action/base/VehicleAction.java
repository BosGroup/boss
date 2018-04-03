package com.imooc.bos.web.action.base;


import java.io.IOException;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.imooc.bos.domain.base.FixedArea;
import com.imooc.bos.domain.base.Vehicle;
import com.imooc.bos.service.base.VehicleService;
import com.imooc.bos.web.action.CommonAction;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JsonConfig;

/**
 * ClassName:VehicleAction <br/>
 * Function: <br/>
 * Date: 2018年4月3日 上午11:24:29 <br/>
 */
@Namespace("/") // 等价于struts.xml文件中package节点namespace属性
@ParentPackage("struts-default") // 等价于struts.xml文件中package节点extends属性
@Scope("prototype") // spring 的注解,多例
@Controller // spring 的注解,控制层代码
public class VehicleAction extends CommonAction<Vehicle>{

    
    public VehicleAction() {
          
        super(Vehicle.class);  
        
    }
    @Autowired
    private VehicleService vehicleService;
    
    @Action(value="vehicleAction_save", results = {@Result(name = "success",
            location = "/pages/base/vehicle.html", type = "redirect")})
    
    
    public String vehicle_save() {

        vehicleService.save(getModel());
        return SUCCESS;
    }
    @Action(value="vehicleAction_findPages")
    public String findPages() throws IOException {
        
        //EasyUI的页码是从1开始的, SPringDataJPA的页码是从0开始的, 所以要-1
        Pageable pageable = new PageRequest(page - 1, rows);
        Page<Vehicle> page = vehicleService.findAll(pageable);

        page2json(page, null);
        return NONE;
    }
    
    @Action(value = "vehicleAction_delVehicle",
            results={@Result(name="success",location="/pages/base/vehicle.html",type="redirect")})
        public String delVehicle(){
        
        vehicleService.delVehicle(getModel().getId());
       
        return SUCCESS;
    }

}
