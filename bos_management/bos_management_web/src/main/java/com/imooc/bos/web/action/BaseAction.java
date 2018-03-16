package com.imooc.bos.web.action;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**  
 * ClassName:BaseAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午10:02:32 <br/>       
 */
public class BaseAction<T> extends ActionSupport implements ModelDriven<T>{

    //模型驱动获取数据,方式二,通过反射获取子类的模型驱动的泛型,不需要子类再传递class类型了
    private T model;
    
    @Override
    public T getModel() {
        //以public class AreaAction extends BaseAction<Area>代码为例
        //调用下面的代码以后,得到的是AreaAction的字节码
        Class<? extends BaseAction> childClazz = this.getClass();
        
        //childClazz.getSuperclass();  得到的是BaseAction
        //childClazz.getGenericSuperclass();  得到的是BaseAction<Area>
        Type genericSuperclass = childClazz.getGenericSuperclass();
        
        //类型强转,为了使用ParameterizedType接口的方法
        ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
        
        //获取泛型的数组
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        
        //获取泛型的字节码文件
        Class<T> clazz = (Class<T>) actualTypeArguments[0];
        
        try {
            model = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();  
        }
        
        return model;
    }


}
  
