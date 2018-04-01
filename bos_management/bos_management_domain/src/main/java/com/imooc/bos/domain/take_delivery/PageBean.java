package com.imooc.bos.domain.take_delivery;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**  
 * ClassName:PageBean <br/>  
 * Function:  <br/>  
 * Date:     2018年3月31日 下午7:53:20 <br/>       
 */


//cxf框架在传输数据的时候,无法对Page对象进行转换,因为这个类是Spring框架提供的,
//我们无法在这个类上方增加一个@XmlRootElement注解
//所以需要自己构建一个对象,来封装分页查询的数据

@XmlRootElement(name = "pageBean")
@XmlSeeAlso({Promotion.class})   //指定泛型的类型,否则CXF框架序列化时,无法将泛型类型序列化
public class PageBean<T> {
    
    private List<T> list;  //当前页的内容
    private long total;   //总数据条数
    
    
    public List<T> getList() {
        return list;
    }
    public void setList(List<T> list) {
        this.list = list;
    }
    public long getTotal() {
        return total;
    }
    public void setTotal(long total) {
        this.total = total;
    }
    
    
}
  
