package com.imooc.bos.web.action.base;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.imooc.bos.domain.base.SubArea;
import com.imooc.bos.service.base.SubAreaService;
import com.imooc.bos.web.action.CommonAction;

/**  
 * ClassName:SubareaAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月16日 下午7:07:55 <br/>       
 */

@Namespace("/") // 等价于struts.xml文件中package节点namespace属性
@ParentPackage("struts-default") // 等价于struts.xml文件中package节点extends属性
@Controller // spring 的注解,控制层代码
@Scope("prototype") // spring 的注解,多例
public class SubareaAction extends CommonAction<SubArea>{
    
    //通过构造方法传递模型驱动所需的对象类型
    public SubareaAction() {
        super(SubArea.class);  
    }
    
    @Autowired
    private SubAreaService subAreaService;
    
    @Action(value="subareaAction_save", results = {@Result(name = "success",
            location = "/pages/base/sub_area.html", type = "redirect")})
    public String save(){
        subAreaService.save(getModel());
        return SUCCESS;
    }
    
}
  
