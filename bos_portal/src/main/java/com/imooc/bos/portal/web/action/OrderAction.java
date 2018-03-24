package com.imooc.bos.portal.web.action;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.imooc.bos.domain.base.Area;
import com.imooc.bos.domain.take_delivery.Order;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**  
 * ClassName:OrderAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月23日 下午3:10:55 <br/>       
 */

@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
@Controller
public class OrderAction extends ActionSupport implements ModelDriven<Order>{
    
    private Order model = new Order();
    
    @Override
    public Order getModel() {
        return model;
    }
    
    
    //######################### 保存订单 ##################################
    //使用属性驱动获取发件人和收件人的区域信息
    private String sendAreaInfo;
    private String recAreaInfo;
    public void setSendAreaInfo(String sendAreaInfo) {
        this.sendAreaInfo = sendAreaInfo;
    }
    public void setRecAreaInfo(String recAreaInfo) {
        this.recAreaInfo = recAreaInfo;
    }
    
    @Action(value = "orderAction_add", results = {@Result(name = "success",
            location = "/index.html", type = "redirect")})
    public String saveOrder() {
        //获取发件人区域数据
        if(StringUtils.isNotEmpty(sendAreaInfo)){
           //切割数据,并去掉省市区
            String[] strs = sendAreaInfo.split("/");
            String province = strs[0].substring(0,strs[0].length()-1);
            String city = strs[1].substring(0,strs[1].length()-1);
            String district =strs[2].substring(0,strs[2].length()-1);
            //封装Area
            Area area = new Area();
            area.setProvince(province);
            area.setCity(city);
            area.setDistrict(district);
            //设置数据
            model.setSendArea(area);
        }
        //获取收件人区域数据
        if(StringUtils.isNotEmpty(recAreaInfo)){
           //切割数据,并去掉省市区
            String[] strs = recAreaInfo.split("/");
            String province = strs[0].substring(0,strs[0].length()-1);
            String city = strs[1].substring(0,strs[1].length()-1);
            String district =strs[2].substring(0,strs[2].length()-1);
            //封装Area
            Area area = new Area();
            area.setProvince(province);
            area.setCity(city);
            area.setDistrict(district);
            //设置数据
            model.setRecArea(area);
        }
        //调用WebService保存订单
        WebClient.create("http://localhost:8080/bos_management_web/webService/orderService/saveOrder")
            .type(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .post(model);
        return SUCCESS;
    }
    
    
}
  
