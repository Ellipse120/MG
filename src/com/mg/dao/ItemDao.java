package com.mg.dao;

import com.mg.vo.Item;

public interface ItemDao {
	
	public int addItem(Item item);
	
	public int deleteItem(Integer itemId);
	
	public int updateItem(Item item);
	
	public Item queryItem(Integer itemId);
	
}
