package com.imooc.bos.web.action.take_delivery;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.imooc.bos.domain.take_delivery.WayBill;
import com.imooc.bos.service.take_delivery.WaybillService;
import com.imooc.bos.web.action.CommonAction;

/**
 * ClassName:WaybillAction <br/>
 * Function: <br/>
 * Date: 2018年3月25日 下午9:26:57 <br/>
 */

@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
@Controller
public class WaybillAction extends CommonAction<WayBill> {

    public WaybillAction() {
        super(WayBill.class);
    }

    @Autowired
    private WaybillService waybillService;
    
    
    //###################### 保存运单 ########################
    @Action("waybillAction_save")
    public String save() throws IOException {
        String msg = "0";
        try {
            //int i =10/0;
            
            waybillService.save(getModel());
        } catch (Exception e) {
            e.printStackTrace();  
            msg = "1";
        }
        
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(msg);
        return NONE;
    }

}
