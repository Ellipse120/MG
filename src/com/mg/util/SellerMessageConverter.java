package com.mg.util;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import com.mg.vo.Seller;
/**
 * 实现了MessageConverter接口，并实现其中的fromMessage和toMessage方法，
 * 分别实现转换接收到的消息为Seller对象和转换Seller对象到消息.
 * @author JFD
 *
 */
public class SellerMessageConverter implements MessageConverter{
	
	private static transient Log logger = LogFactory.getLog(SellerMessageConverter.class);
	@Override
	public Message toMessage(Object object, Session session)
			throws JMSException, MessageConversionException {
		if(logger.isDebugEnabled()){
			logger.debug("Convert Seller to JMS messsage:"+object);
		}
		if(object instanceof Seller){
			 ActiveMQObjectMessage amsg = (ActiveMQObjectMessage)session.createObjectMessage();
			 amsg.setObject((Seller)object);
			 return amsg;
		}else{
			logger.error(object+" is not a instanceof Seller");
			throw new JMSException(object+" is not a instanceof Seller");
		}
		
	}

	@Override
	public Object fromMessage(Message message) throws JMSException,
			MessageConversionException {
		if(logger.isDebugEnabled()){
			logger.debug("Receive JMS messsage:"+message);
		}
		
		if(message instanceof ObjectMessage){
			ObjectMessage om = (ObjectMessage) message;
			if(om instanceof ActiveMQObjectMessage){
				 ActiveMQObjectMessage amom = (ActiveMQObjectMessage) om;
				 Seller seller = (Seller) amom.getObject();
				 return seller;
			}else{
				logger.error(message+" is not a instanceof ActiveMQObjectMessage");
				throw new JMSException(message+" is not a instanceof ActiveMQObjectMessage");
			}
		}else{
			logger.error(message+" is not a instanceof ObjectMessage");
			throw new JMSException(message+" is not a instanceof ObjectMessage");
		}
	
	}

}
