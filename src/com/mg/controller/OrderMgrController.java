package com.mg.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.servlet.http.HttpServletResponse;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mg.vo.Order;

@Controller
public class OrderMgrController {
	@Resource(name="pooledConnectionFactory")
	private PooledConnectionFactory factory;
	@Resource(name="queueOrder")
	private ActiveMQQueue queueOrder;
	
	@RequestMapping("/orderShow")
	@ResponseBody
	public Order showOrder(@RequestBody Order order)throws Exception{
		Connection conn = factory.createConnection();
		conn.start();
		
		Session sen = conn.createSession(false, Session.CLIENT_ACKNOWLEDGE);
		MessageConsumer consumer = sen.createConsumer(queueOrder);
		
		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message msg) {

				TextMessage tm = (TextMessage) msg;
		
					try {
						String s = tm.getText();
						
						JSONObject json = new JSONObject(s);
						
						System.out.println(json);
						
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
		return order;

	}
	
}
