package com.mg.controller;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.pool.PooledConnectionFactory;
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
	
	@Resource(name="pooledConnectionFactory")
	private PooledConnectionFactory factory;
	@Resource(name="queueItemPutOn")
	private ActiveMQQueue queueItemPutOn;
	@Resource(name="queueItemPutOff")
	private ActiveMQQueue queueItemPutOff;
	@Resource(name="queueItemUpdate")
	private ActiveMQQueue queueItemUpdate;
	
	//商品上架
	@RequestMapping("/itemPutOnShelves")
	public int itemPutOnShelves(Item item,HttpServletRequest request)throws Exception{
		
		Connection conn = factory.createConnection();
		conn.start();
		
		Session sen = conn.createSession(false, Session.CLIENT_ACKNOWLEDGE);
		MessageProducer producer = sen.createProducer(queueItemPutOn);
		
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
	@RequestMapping("/itemPullOffShelves")
	public int itemPullOffShelves(Item item,HttpServletRequest request)throws Exception{
		
		Connection conn = factory.createConnection();
		conn.start();
		
		Session sen = conn.createSession(false, Session.CLIENT_ACKNOWLEDGE);
		MessageProducer producer = sen.createProducer(queueItemPutOff);
		
		int flag = its.itemPullOffShelves(item.getItemId());
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
	@RequestMapping("/itemUpdate")
	public int itemUpdate(Item item,HttpServletRequest request)throws Exception{
		
		Connection conn = factory.createConnection();
		conn.start();
		
		Session sen = conn.createSession(false, Session.CLIENT_ACKNOWLEDGE);
		MessageProducer producer = sen.createProducer(queueItemUpdate);
		
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
	
	//查询商品
		@RequestMapping("/itemQuery")
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
