package com.imooc.activemq;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.stereotype.Component;

import com.imooc.bos.bosUtils.MailUtils;
import com.imooc.bos.bosUtils.SmsUtils;
@Component
public class MailConsumer implements MessageListener{

	@Override
	public void onMessage(Message message) {
		 MapMessage mapMessage = (MapMessage) message;
         try {
           String emailBody = mapMessage.getString("emailBody");
           String mail = mapMessage.getString("mail");
           System.out.println(emailBody+":"+mail);
           //发送邮件
//           MailUtils.sendMail("激活邮件", emailBody, mail);
       } catch (Exception e) {
           e.printStackTrace();
	}

}
}