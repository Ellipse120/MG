package com.mg.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mg.vo.Order;

@Controller
public class OrderMgrController {
	@RequestMapping("/orderShow")
	public void showOrder(Order Order,
			HttpServletResponse response)throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		ConnectionFactory factory = (ConnectionFactory) context.getBean("targetConnectionFactory");
		Connection conn = factory.createConnection();
		conn.start();
		
		Destination queue = (Destination) context.getBean("queueOrder");
		Session sen = conn.createSession(false, Session.CLIENT_ACKNOWLEDGE);
		MessageConsumer consumer = sen.createConsumer(queue);
		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message msg) {

				TextMessage tm = (TextMessage) msg;
		
					try {
						String s = tm.getText();
						JSONObject json = new JSONObject(s);
						response.getWriter().print(json.toString());
						//Double unitPrice = (Double) json.get("unitPrice");
						//int amount = (int) json.get("amount");
						String orderName = (String) json.get("orderName");
						System.out.println(orderName);
						
						FileWriter fw = null;
						File f = new File("order.log");
						
						if (!f.exists()) {
							f.createNewFile();
						}
						fw = new FileWriter(f,true);
						BufferedWriter out = new BufferedWriter(fw);
						out.write("\r\n" + s, 0, s.length() - 1);
						out.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
					
			}
		});

	}
}
