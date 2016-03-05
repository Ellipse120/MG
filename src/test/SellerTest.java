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
	public  void sellerLoginTest(){
		Seller seller = ss.login("catlina", "123");
	}
	@Test
	public  void sellerRegistTest(){
		Seller seller = new Seller();
		seller.setSellerName("catlina");
		seller.setPassword("123");
		ss.regist(seller);
	}
}
