package com.mail;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.mail.Session;

import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * 从MQ上接收消息（邮箱和验证码），然后将验证码发到此用户邮箱中。
 * @author JFD
 *
 */
public class SendEmail {
	
	@SuppressWarnings("resource")
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
						System.out.println("消费成功： "+email+" "+sendCode);
						//创建邮件
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
