package com.imooc.bos.web.action.base;

import java.io.IOException;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.imooc.bos.domain.base.TakeTime;
import com.imooc.bos.service.base.TakeTimeService;
import com.imooc.bos.web.action.CommonAction;

/**  
 * ClassName:TakeTimeAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月20日 下午11:28:04 <br/>       
 */

@Namespace("/") // 等价于struts.xml文件中package节点namespace属性
@ParentPackage("struts-default") // 等价于struts.xml文件中package节点extends属性
@Scope("prototype") // spring 的注解,多例
@Controller // spring 的注解,控制层代码
public class TakeTimeAction extends CommonAction<TakeTime>{
    
    //通过构造方法传递模型驱动所需的对象类型
    public TakeTimeAction() {
        super(TakeTime.class);  
    }
    
    @Autowired
    private TakeTimeService takeTimeService;
    
    
    //################### 查询所有收派时间  ####################
    @Action("takeTimeAction_listajax")
    public String listajax() throws IOException {

        List<TakeTime> list = takeTimeService.findAll();

        list2json(list, null);
        return NONE;
    }
}
  
