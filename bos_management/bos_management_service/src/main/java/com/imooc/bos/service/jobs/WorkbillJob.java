package com.imooc.bos.service.jobs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.imooc.bos.bosUtils.MailUtils;
import com.imooc.bos.dao.take_delivery.WorkBillRepository;
import com.imooc.bos.domain.take_delivery.WorkBill;

/**  
 * ClassName:WorkbillJob <br/>  
 * Function:  <br/>  
 * Date:     2018年3月30日 下午5:53:40 <br/>       
 */

@Component
public class WorkbillJob {
    
    @Autowired
    private WorkBillRepository workBillRepository;
    
    public void sendMail(){
        List<WorkBill> list = workBillRepository.findAll();
        
        String emailBody = "编号\t快递员\t取件状态\t时间<br/>";
        
        for (WorkBill workBill : list) {
            emailBody += workBill.getId()+"\t"+workBill.getCourier().getName()+"\t"+
                    workBill.getPickstate()+"\t"+workBill.getBuildtime()+"\t";
        }
        MailUtils.sendMail("工单信息统计", emailBody, "1285920790@qq.com");
        //System.out.println("邮件已经发送");
    }
}
  
