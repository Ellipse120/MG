package com.mg.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mg.dao.ItemDao;
import com.mg.service.ItemService;
import com.mg.vo.Item;

@Service("ii")
public class ItemServiceImpl implements ItemService{
	
		@Autowired(required=true)
		private ItemDao itd;

		@Override
		@Transactional
		public int itemPutOnShelves(Item item) {
			return itd.addItem(item);
		}

		@Override
		@Transactional
		public int itemPullOffShelves(Integer itemId) {
			return itd.deleteItem(itemId);
		}

		@Override
		@Transactional
		public int itemUpdate(Item item) {
			return itd.updateItem(item);
		}

		@Override
		public Item itemQuery(Integer itemId) {
			return itd.queryItem(itemId);
			
		}
}
