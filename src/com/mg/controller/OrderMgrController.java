package com.mg.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mg.vo.Order;
import com.mg.vo.User;

@Controller
public class OrderMgrController {
	@Resource(name="pooledConnectionFactory")
	private PooledConnectionFactory factory;
	@Resource(name="queueOrder")
	private ActiveMQQueue queueOrder;
	Order or;
	JSONObject json;
	List<Order> list =new ArrayList<Order>();;
	@RequestMapping(value="common/orderShow",method=RequestMethod.GET)
	@ResponseBody
	public List<Order> showOrder(HttpServletRequest request)throws Exception{
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
						
						json = new JSONObject(s);
						System.out.println(json);
						
						 or = new Order();
						String orderNum = json.getString("orderNum"); 
						String orderInfo = json.getString("orderInfo");
						String uName = json.getString("uName");
						String address = json.getString("address");
						String phoneNum = json.getString("phoneNum");
						//String orderStatus = json.getString("orderStatus");
						
						/*User user  = new User();
						user.setUserName(uName);
						user.setAddress(address);
						user.setPhoneNum(phoneNum);*/
						
						or.setOrderNum(orderNum);
						or.setOrderInfo(orderInfo);
						or.setOrderStatus(true);
						or.setuName(uName);
						or.setAddress(address);
						or.setPhone(phoneNum);
						
						
						list.add(or);
						
						/*
						 * 将订单存到日志
						 */
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
		
		System.out.println(list.size());
		return list;
		
	}
	
}
