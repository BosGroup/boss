package com.imooc.activemq;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.stereotype.Component;

import com.imooc.bos.bosUtils.SmsUtils;

/**  
 * ClassName:SMSConsumer <br/>  
 * Function:  <br/>  
 * Date:     2018年3月25日 下午7:49:25 <br/>       
 */

@Component
public class SMSConsumer implements MessageListener{

    @Override
    public void onMessage(Message message) {
          MapMessage mapMessage = (MapMessage) message;
          try {
            String telephone = mapMessage.getString("tel");
            String code = mapMessage.getString("code");
            System.out.println(telephone+":"+code);
            //发送短信
            SmsUtils.sendSms(telephone, code);
        } catch (Exception e) {
            e.printStackTrace();  
        }
        
    }
    
    
}
  
