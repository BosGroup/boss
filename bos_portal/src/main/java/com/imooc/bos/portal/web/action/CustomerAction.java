package com.imooc.bos.portal.web.action;

import com.imooc.bos.crm.domain.Customer;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**  
 * ClassName:CustomerAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月21日 下午8:48:27 <br/>       
 */
public class CustomerAction extends ActionSupport implements ModelDriven<Customer>{
    
    private Customer customer = new Customer();
    
    @Override
    public Customer getModel() {
        return customer;
    }

}
  
