package com.imooc.crm.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ctc.wstx.util.StringUtil;
import com.imooc.crm.dao.CustomerRepository;
import com.imooc.crm.domain.Customer;
import com.imooc.crm.service.CustomerService;

/**  
 * ClassName:CustomerServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月19日 下午3:41:33 <br/>       
 */

@Transactional
@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public List<Customer> findCustomersUnAssociated() {
        return customerRepository.findByFixedAreaIdIsNull();
    }

    @Override
    public List<Customer> findCustomersAssociated2FixedArea(String fixedAreaId) {
        return customerRepository.findByFixedAreaId(fixedAreaId);
    }

    @Override
    public void assignCustomers2FixedArea(String fixedAreaId, Long[] customerIds) {
        //根据定区ID,把关联到这个定区的所有客户全部解绑
        if(StringUtils.isNotEmpty(fixedAreaId)){
            customerRepository.unbindCustomerByFixedArea(fixedAreaId);
        }
        
        //要关联的数据和定区Id进行绑定
        if(customerIds != null && fixedAreaId.length()>0){
            for (Long customerId : customerIds) {
                 customerRepository.bindCustomerByFixedArea(fixedAreaId,customerId);
            }
        }
    }
}
  
