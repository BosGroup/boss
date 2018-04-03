package com.imooc.activemq;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.stereotype.Component;
@Component
public class MailConsumer implements MessageListener{

	@Override
	public void onMessage(Message message) {
		 MapMessage mapMessage = (MapMessage) message;
         try {
           String emailBody = mapMessage.getString("emailBody");
           String mail = mapMessage.getString("mail");
           System.out.println(emailBody+":"+mail);
           //发送短信
           //SmsUtils.sendSms(telephone, code);
       } catch (Exception e) {
           e.printStackTrace();
	}

}
}