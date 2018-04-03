package com.imooc.bos.web.action.base;

import java.io.IOException;
import java.util.List;

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

import com.imooc.bos.domain.base.TakeTime;
import com.imooc.bos.service.base.TakeTimeService;
import com.imooc.bos.web.action.CommonAction;

/**  
 * ClassName:TakeTimeAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月21日 下午4:14:22 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class TakeTimeAction extends CommonAction<TakeTime> {

    public TakeTimeAction() {
        super(TakeTime.class);  
    }
    
    @Autowired
    private TakeTimeService takeTimeService;

    @Action(value="taketimeAction_save", results = {
            @Result(name = "success", location = "/pages/base/take_time.html", type = "redirect")})
    public String save(){
        takeTimeService.save(getModel());
        return SUCCESS;
    }
    
    @Action(value = "taketimeAction_pageQuery")
    // AJAX不需要跳转页面
    public String pageQuery() throws IOException {
        // easyui页码从1开始,而SpringDataJPA的页面从0开始,所有page要减1
        Pageable pageable = new PageRequest(page - 1, rows);
        Page<TakeTime> page = takeTimeService.findAll(pageable);

        page2json(page, null);
        return NONE;
    }
    
    @Action(value = "taketimeAction_listajax")
    public String listajax2() throws IOException {
        List<TakeTime> list = takeTimeService.findAll();
        list2json(list, null);
        return NONE;
    }
}
  
