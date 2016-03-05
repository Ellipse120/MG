package test;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mg.service.ItemService;
import com.mg.vo.Item;

public class ItemTest {
	private static ItemService is;
	@BeforeClass
	public static void beforeClass(){
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		is = (ItemService) context.getBean("ii");
	}
	
	@Test
	public void itemPutOnSelvesTest(){
		Item item = new Item();
		item.setItemName("cola");
		item.setUnitPrice(7.0);
		item.setAmount(3);
		is.itemPutOnShelves(item);
	}
	@Test
	public void itemPullOffSelvesTest(){
		is.itemPullOffShelves(2);
	}
	@Test
	public void itemUpdateTest(){
		Item item  = new Item();
		item.setItemId(1);
		item.setUnitPrice(20.0);
		is.itemUpdate(item);
	}
	@Test
	public void itemQueryTest(){
		is.itemQuery(1);
	}
}
