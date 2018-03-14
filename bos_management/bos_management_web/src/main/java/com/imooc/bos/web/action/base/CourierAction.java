package com.imooc.bos.web.action.base;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
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

import com.imooc.bos.domain.base.Courier;
import com.imooc.bos.service.base.CourierService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**  
 * ClassName:CourierAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午7:48:13 <br/>       
 */

@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
@Controller
public class CourierAction extends ActionSupport implements ModelDriven<Courier>{
    
    private Courier model = new Courier();
    @Override
    public Courier getModel() {
          
        return model;
    }
    
    @Autowired
    private CourierService courierService;
    
    
    //################### 保存快递员信息  ####################
    @Action(value = "courierAction_save", 
       results = {@Result(name = "success",location="/pages/base/courier.html", type="redirect")})
    public String save(){
        courierService.save(model);
        return SUCCESS;
    }
    
    
    //################### 分页查询快递员信息  ####################
    private int page;
    private int rows;
    public void setPage(int page) {
        this.page = page;
    }
    public void setRows(int rows) {
        this.rows = rows;
    }

    @Action("courierAction_pageQuery")
    public String pageQuery() throws IOException{
        
        Pageable pageable = new PageRequest(page - 1, rows);
        Page<Courier> page = courierService.findAll(pageable);
        
        //封装数据
        long total = page.getTotalElements();   // 总数据条数
        List<Courier> list = page.getContent();   // 当前页的内容
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", list);
        
        // 在实际开发的时候,为了提高服务器的性能,把前台页面不需要的数据都应该忽略掉
        // 灵活控制输出的内容
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"fixedAreas", "takeTime"});

        // 把对象转化为json字符串, JSONObject:封装对象或map集合,JSONArray:数组,list集合,将jsonConfig传进去
        String json = JSONObject.fromObject(map, jsonConfig).toString();
        
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
        
        return NONE;
    }
    
    
    //################### 批量删除快递员信息  ####################
    //使用属性驱动获取要删除的快递员的Id
    private String ids;
    public void setIds(String ids) {
        this.ids = ids;
    }
    
    @Action(value = "courierAction_batchDel",
        results={@Result(name="success",location="/pages/base/courier.html",type="redirect")})
    public String batchDel(){
        courierService.batchDel(ids);
        return SUCCESS;
    }
    
}
  
