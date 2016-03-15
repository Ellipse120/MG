package com.mail;

import java.text.MessageFormat;
import java.util.Properties;
import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.mail.Session;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
@Component
public class SendEmail {
	@Resource(name="pooledConnectionFactory")
	private PooledConnectionFactory factory;
	@Resource(name="queueUpdatePwd")
	private ActiveMQQueue queueUpdatePwd;
	
	public void mailConsumer() throws JMSException{
		Connection conn = factory.createConnection();
		conn.start();
		
		javax.jms.Session sen = conn.createSession(false, javax.jms.Session.CLIENT_ACKNOWLEDGE);
		MessageConsumer consumer = sen.createConsumer(queueUpdatePwd);
		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message msg) {

				TextMessage tm = (TextMessage) msg;
		
					try {
						String s = tm.getText();
						JSONObject json = new JSONObject(s);
						String email = json.getString("email");
						String sendCode = json.getString("sendCode");
						
						Properties props = new Properties();
						props.load(this.getClass().getClassLoader().getResourceAsStream("email.properties"));
						String host = props.getProperty("host");
						String uname = props.getProperty("uname");
						String pwd = props.getProperty("pwd");
						String from = props.getProperty("from");
						String to = email;
						String subject = props.getProperty("subject");
						String content = props.getProperty("content");
						content = MessageFormat.format(content, sendCode);
						Session session = MakeEmail.createSession(host, uname, pwd);
						Email em = new Email(from,to,subject,content);
						
						MakeEmail.send(session, em);
					} catch (Exception e) {
						System.out.println("Email is failed to send....");
					}
					
			}
		});
		
		
	}
}
