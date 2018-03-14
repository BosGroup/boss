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

import com.imooc.bos.domain.base.Standard;
import com.imooc.bos.service.base.StandardService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**  
 * ClassName:StandardAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午3:04:18 <br/>       
 */

//类名中要有action,actions,struts,struts2
@Namespace("/")      // 等价于struts.xml文件中package节点namespace属性
@ParentPackage("struts-default")      // 等价于struts.xml文件中package节点extends属性
@Scope("prototype")      // spring的注解,多例
@Controller         // spring的注解,控制层代码
public class StandardAction extends ActionSupport implements ModelDriven<Standard>{
    
    //模型驱动获取数据
    private Standard model = new Standard();
    @Override
    public Standard getModel() {
          
        return model;
    }
    
    @Autowired
    private StandardService standardService;
    
    //################### 保存取派标准 ####################
    // value:等价于struts.xml文件中action节点中的name属性, 多个结果就使用@Result注解标识
    // name:结果, location:跳转页面路径, type:使用的方式,重定向或转发
    @Action(value = "standardAction_save", 
            results={@Result(name="success", location="/pages/base/standard.html", type="redirect")})
    public String save(){
      standardService.save(model);
      return SUCCESS;  
    }
    
    //################### 分页查询取派标准  ####################
    //使用属性驱动获取数据
    private int page;   // 第几页
    private int rows;   // 每一页显示多少条数据
    public void setPage(int page) {
        this.page = page;
    }
    public void setRows(int rows) {
        this.rows = rows;
    }
    
    // AJAX请求不需要跳转页面
    @Action(value = "standardAction_pageQuery")
    public String pageQuery() throws IOException{
        
        // EasyUI的页码是从1开始的, SPringDataJPA的页码是从0开始的, 所以要-1
        Pageable pageable = new PageRequest(page - 1, rows);
        Page<Standard> page = standardService.findAll(pageable);
        
        //封装数据
        long total = page.getTotalElements();   // 总数据条数
        List<Standard> list = page.getContent();   // 当前页的内容
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", list);
        
        // 把对象转化为json字符串, JSONObject:封装对象或map集合,JSONArray:数组,list集合
        String json = JSONObject.fromObject(map).toString();
        
        // ServletContext servletContext = ServletActionContext.getServletContext();
        // servletContext.getRealPath("");
        // servletContext.getMimeType("");
        
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
        
        return NONE;
    }
    
    //################### 查询所有的派送标准  ####################
    @Action(value = "standard_findAll")
    public String findAll() throws IOException{
        // findAll(pageable)分页查询, findAll()查询所有
        // 查询数据,可以使用分页查询的方法,但传null调用无参构造
        Page<Standard> page = standardService.findAll(null);
        
        // 获取页面的数据
        List<Standard> list = page.getContent();
        
        // 转换数据为json并传回页面
        String json = JSONArray.fromObject(list).toString();
        
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
        
        return NONE;
    }
}
  
