package test;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mg.service.SellerService;
import com.mg.vo.Seller;

public class SellerTest {
	private static SellerService ss;
	@BeforeClass
	public static void beforeClass(){
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		ss = (SellerService) context.getBean("si");
	}
	@Test
	public  void testSellerLogin(){
		Seller seller = ss.login("catalina", "123");
		System.out.println(seller);
	}
	@Test
	public  void testSellerRegist(){
		Seller seller = new Seller();
		seller.setSellerName("catalina");
		seller.setPassword("123");
		ss.regist(seller);
	}
}
