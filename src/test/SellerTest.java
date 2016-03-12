package test;

import java.util.Date;

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
		Seller seller = ss.login("damon", "123");
		System.out.println(seller);
	}
	@Test
	public  void testSellerRegist(){
		Seller seller = new Seller();
		seller.setSellerName("damon");
		seller.setEmail("851590981@qq.com");
		seller.setPassword("123");
		seller.setPhone("10086");
		seller.setAddress("nanjing");
		seller.setDate(new Date());
		seller.setType("KFC");
		seller.setLicencefile("abc");
		ss.regist(seller);
	}
}
