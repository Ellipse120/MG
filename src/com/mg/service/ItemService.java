package com.mg.service;

import com.mg.vo.Item;

public interface ItemService {
	
	public int itemPutOnShelves(Item item);
	
	public int itemPullOffShelves(Integer itemId);
	
	public int itemUpdate(Item item);
	
	public Item itemQuery(Integer itemId);
}
