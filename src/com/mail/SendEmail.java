package com.mail;

import java.text.MessageFormat;
import java.util.Properties;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.mail.Session;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.jboss.jandex.Main;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

public class SendEmail {
	
	public static void mailConsumer() throws JMSException{
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		ConnectionFactory factory = (ConnectionFactory) context
				.getBean("pooledConnectionFactory");
		Destination queue = (Destination) context.getBean("queueUpdatePwd");
		Connection conn = factory.createConnection();
		conn.start();
		javax.jms.Session sen = conn.createSession(false, javax.jms.Session.CLIENT_ACKNOWLEDGE);
		MessageConsumer consumer = sen.createConsumer(queue);
		
		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message msg) {
				
				TextMessage tm = (TextMessage) msg;
		
					try {
						String s = tm.getText();
						JSONObject json = new JSONObject(s);
						String email = json.getString("email");
						String sendCode = json.getString("sendCode");
						System.out.println(sendCode);
						System.out.println(email);
						
						Properties props = new Properties();
						props.load(this.getClass().getClassLoader().getResourceAsStream("email.properties"));
						String host = props.getProperty("mail.host");
						String uname = props.getProperty("mail.username");
						String pwd = props.getProperty("mail.password");
						String from = props.getProperty("mail.from");
						String to = "jfd1@jfd";
						//String to = "851590981@qq.com";
						String subject = props.getProperty("mail.subject");
						String content = sendCode;
						Session session = MakeEmail.createSession(host, uname, pwd);
						Email em = new Email(from,to,subject,content);
						System.out.println("Sending...");
						MakeEmail.send(session, em);
						System.out.println("Send successfully");
					} catch (Exception e) {
						System.out.println("Email is failed to send....");
					}
					
			}
		});
		
		
	}
	
	public static void main(String[] args) throws JMSException {
		mailConsumer();
	}
}
