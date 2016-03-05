package com.mg.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.mg.dao.ItemDao;
import com.mg.vo.Item;

@Repository("id")
public class ItemDaoImpl implements ItemDao{
	@PersistenceContext(name="unitName")
	private EntityManager em;
	@Override
	public int addItem(Item item) {
		em.persist(item);
		return item.getItemId();
	}

	@Override
	public int deleteItem(Integer itemId) {
		String jpql = "delete from Item i where i.itemId=:id";
		@SuppressWarnings("unchecked")
		Query query=em.createQuery(jpql);
		query.setParameter("id", itemId);
		query.executeUpdate();
		return itemId;
	}
	
	@Override
	public int updateItem(Item item) {
		String jpql = "update Item i set i.unitPrice=:price where i.itemId=:id";
		@SuppressWarnings("unchecked")
		Query query=em.createQuery(jpql);
		query.setParameter("price", item.getUnitPrice());
		query.setParameter("id", item.getItemId());
		query.executeUpdate();
		return item.getItemId();
	}
	
	@Override
	public Item queryItem(Integer itemId) {
		String jpql = "select i from Item i where i.itemId=:id";
		@SuppressWarnings("unchecked")
		List<Item> list = em.createQuery(jpql)
				.setParameter("id", itemId)
				.getResultList();
		if(list.isEmpty())
			return null;
		else
		return list.get(0);
		
	}

	

}
