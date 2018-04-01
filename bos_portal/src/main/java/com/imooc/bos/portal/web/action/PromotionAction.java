package com.imooc.bos.portal.web.action;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.imooc.bos.domain.take_delivery.PageBean;
import com.imooc.bos.domain.take_delivery.Promotion;
import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONObject;

/**  
 * ClassName:PromotionAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月31日 下午10:04:13 <br/>       
 */

@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
@Controller
public class PromotionAction extends ActionSupport{
    
    //属性驱动获取当前页码和每一页显示多少条数据
    private int pageIndex;
    private int pageSize;

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    
    
    //################ 宣传任务分页查询 ###################
    @Action(value = "promotionAction_pageQuery")
    public String pageQuery() throws IOException {
        
        PageBean<Promotion> pageBean = WebClient.create(
                "http://localhost:8080/bos_management_web/webService/promotionService/findAll4Fore")
                .type(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .query("pageIndex", pageIndex)//
                .query("pageSize", pageSize)
                .get(PageBean.class);
        
        //返回json数据
        String json = JSONObject.fromObject(pageBean).toString();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
        
        return NONE;
    }
}
  
