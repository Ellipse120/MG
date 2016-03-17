package test;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.json.JSONObject;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EmailTest {
	
	@Test
	public  void testSellerLogin(){
		/*ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		ConnectionFactory factory = (ConnectionFactory)context.getBean("targetConnectionFactory");
		Destination queue = (Destination)context.getBean("queueDestination");
		javax.jms.Connection con = factory.createConnection();
		con.start();
		
		JSONObject json = new JSONObject();
		Session session=null;
		MessageProducer pro=null;
		TextMessage tmsg;*/
	}
}
