package com.imooc.bos.service.take_delivery;


import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.imooc.bos.domain.take_delivery.PageBean;
import com.imooc.bos.domain.take_delivery.Promotion;

/**  
 * ClassName:PromotionService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月31日 下午3:59:23 <br/>       
 */

@Consumes(MediaType.APPLICATION_JSON)   //指定参数的数据传输格式,若写在接口上,对所有方法都有效
@Produces(MediaType.APPLICATION_JSON)   //指定返回值的数据传输格式,若写在接口上,对所有方法都有效
public interface PromotionService {

    /**  
     * 保存宣传任务
     * @param promotion  
     */
    void save(Promotion promotion);

    /**  
     * 宣传任务分页查询
     * @param pageable
     * @return  
     */
    Page<Promotion> findAll(Pageable pageable);
    
    
    //无法在Page类上加@XmlRootElement注解,这里需要自己创建一个PageBean
    @GET
    @Path("/findAll4Fore")
    PageBean<Promotion> findAll4Fore(@QueryParam("pageIndex") int page,
            @QueryParam("pageSize") int pageSize);
    
    
}
  
