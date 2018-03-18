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
import com.imooc.bos.service.base.FixedAreaService;
import com.imooc.bos.web.action.CommonAction;

import net.sf.json.JsonConfig;

/**  
 * ClassName:FixedAreaAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午9:43:52 <br/>       
 */

@Namespace("/")    //等价于struts.xml文件中package节点namespace属性
@ParentPackage("struts-default")  //等价于struts.xml文件中package节点extends属性
@Scope("prototype") //spring 的注解,多例
@Controller   //spring的注解,控制层代码
public class FixedAreaAction extends CommonAction<FixedArea> {
    
    //通过构造方法传递模型驱动所需的对象类型
    public FixedAreaAction(){
        super(FixedArea.class);
    }
    
    @Autowired
    private FixedAreaService fixedAreaService;
    
    
    //################### 保存定区 ####################
    @Action(value = "fixedAreaAction_save", results = {@Result(name = "success",
            location = "/pages/base/fixed_area.html", type = "redirect")})
    public String save(){
        fixedAreaService.save(getModel());
        return SUCCESS;
    }
    
    
   //################### 分页查询定区  ####################
 // AJAX请求不需要跳转页面
    @Action(value = "fixedAreaAction_pageQuery")
    public String pageQuery() throws IOException {
        
        //EasyUI的页码是从1开始的, SPringDataJPA的页码是从0开始的, 所以要-1
        Pageable pageable = new PageRequest(page - 1, rows);
        Page<FixedArea> page = fixedAreaService.findAll(pageable);

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"subareas", "couriers"});

        page2json(page, jsonConfig);
        return NONE;
    }
}
  
