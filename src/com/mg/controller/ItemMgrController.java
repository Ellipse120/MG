package com.mg.controller;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mg.service.ItemService;
import com.mg.vo.Item;
@Controller
public class ItemMgrController {
	
	@Autowired(required=true)
	private ItemService its;
	//商品上架
	@RequestMapping("/itemPutOnShelves")
	public int itemPutOnShelves(Item item,HttpServletRequest request)throws Exception{
		
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		ConnectionFactory factory = (ConnectionFactory) context.getBean("targetConnectionFactory");
		Connection conn = factory.createConnection();
		conn.start();
		
		Destination queue = (Destination) context.getBean("queueItem");
		Session sen = conn.createSession(false, Session.CLIENT_ACKNOWLEDGE);
		MessageProducer producer = sen.createProducer(queue);
		
		int flag = its.itemPutOnShelves(item);
		if(flag>0){
			JSONObject json = new JSONObject();
			json.put("itemId", item.getItemId());
			json.put("itemName", item.getItemName());
			json.put("uitPrice", item.getUnitPrice());
			TextMessage msg = sen.createTextMessage(json.toString());
			producer.send(msg);
		}
		return flag;
		
	}
	
	//商品下架
	@RequestMapping("/itemPutOnShelves")
	public int itemPullOffShelves(Item item,HttpServletRequest request)throws Exception{
		
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		ConnectionFactory factory = (ConnectionFactory) context.getBean("targetConnectionFactory");
		Connection conn = factory.createConnection();
		conn.start();
		
		Destination queue = (Destination) context.getBean("queueDestination");
		Session sen = conn.createSession(false, Session.CLIENT_ACKNOWLEDGE);
		MessageProducer producer = sen.createProducer(queue);
		
		int flag = its.itemPullOffShelves(item);
		if(flag>0){
			JSONObject json = new JSONObject();
			json.put("itemId", item.getItemId());
			json.put("itemName", item.getItemName());
			json.put("uitPrice", item.getUnitPrice());
			TextMessage msg = sen.createTextMessage(json.toString());
			producer.send(msg);
		}
		return flag;
		
	}
	
	//商品更新
	@RequestMapping("/itemPutOnShelves")
	public int itemUpdate(Item item,HttpServletRequest request)throws Exception{
		
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		ConnectionFactory factory = (ConnectionFactory) context.getBean("targetConnectionFactory");
		Connection conn = factory.createConnection();
		conn.start();
		
		Destination queue = (Destination) context.getBean("queueDestination");
		Session sen = conn.createSession(false, Session.CLIENT_ACKNOWLEDGE);
		MessageProducer producer = sen.createProducer(queue);
		
		int flag = its.itemUpdate(item);
		if(flag>0){
			JSONObject json = new JSONObject();
			json.put("itemId", item.getItemId());
			json.put("itemName", item.getItemName());
			json.put("uitPrice", item.getUnitPrice());
			TextMessage msg = sen.createTextMessage(json.toString());
			producer.send(msg);
		}
		return flag;
		
	}
	
	//商品查询
		@RequestMapping("/itemPutOnShelves")
		public String itemQuery(Item item,HttpServletRequest request)throws Exception{
			
			Item item1 = its.itemQuery(item.getItemId());
			if(item1!=null){
				request.getSession().setAttribute("item", item1);
				return "";
			}else{
				return "";
			}
			
		}
}
