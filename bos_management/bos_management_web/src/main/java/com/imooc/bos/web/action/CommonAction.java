package com.imooc.bos.web.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.formula.functions.T;
import org.apache.struts2.ServletActionContext;
import org.springframework.data.domain.Page;

import com.imooc.bos.domain.base.Standard;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**  
 * ClassName:CommonAction <br/>  
 * Function:  公共基类
 * Date:     2018年3月15日 下午10:04:40 <br/>       
 */
public class CommonAction<T> extends ActionSupport implements ModelDriven<T>{
    
    //模型驱动获取数据,方式一,通过子类的构造函数传递所需的对象类型
    private T model;
    private Class<T> clazz;
    
    public CommonAction(Class<T> clazz) {
        this.clazz = clazz;
    }
    
    @Override
    public T getModel() {
        try {
            //必须判断是否为空,属性驱动获取值时model有值不为null
            if(model == null){
                model = clazz.newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();  
        }  
        return model;
    }

    
    //################### 抽取分页查询公共部分  ####################
    //使用属性驱动获取数据,注意此处将private改成protected,以便子类中能使用此属性
    //protected对子类公开,对外部类私有
    protected int page;   // 第几页
    protected int rows;   // 每一页显示多少条数据
    public void setPage(int page) {
        this.page = page;
    }
    public void setRows(int rows) {
        this.rows = rows;
    }
    
    public void page2json(Page<T> page, JsonConfig jsonConfig) throws IOException {
        
        //封装数据,Easy需要特定格式的json,要有total和rows两个key,可以用javabean或map封装
        long total = page.getTotalElements();   // 总数据条数
        List<T> list = page.getContent();   // 当前页的内容

        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", list);
        
        //把对象转化为json字符串, JSONObject:封装对象或map集合,JSONArray:数组,list集合
        String json;
        if (jsonConfig != null) {
            json = JSONObject.fromObject(map, jsonConfig).toString();
        } else {
            json = JSONObject.fromObject(map).toString();
        }

        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
    }
    
    
    //################### 将list转换为json并写出到前端  ####################
    public void list2json(List<T> list, JsonConfig jsonConfig) throws IOException{
        String json;
        if(jsonConfig != null){
            json = JSONArray.fromObject(list, jsonConfig).toString();
        }else{
            json = JSONArray.fromObject(list).toString();
        }
        
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
    }
}
  
