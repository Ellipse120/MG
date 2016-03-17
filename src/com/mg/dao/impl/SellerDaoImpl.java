package com.mg.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.mg.dao.SellerDao;
import com.mg.vo.Seller;

	@Repository("sd")//bean的id
	public class SellerDaoImpl implements SellerDao {
		@PersistenceContext(name="unitName")
		private EntityManager em;
		@Override
		public int addSeller(Seller seller) {
			em.persist(seller);
			return seller.getSellerId();
		}

		@Override
		public Seller querySeller(String sellerName, String password) {
			String jpql="select s from Seller s where s.sellerName=:name and s.password=:pwd";
			@SuppressWarnings("unchecked")
			List<Seller> list = em.createQuery(jpql)
					.setParameter("name", sellerName)
					.setParameter("pwd", password)
					.getResultList();
			if(list.isEmpty())
				return null;
			else
			return list.get(0);
		}

		@Override
		public void changePassword(Seller seller) {
			String jpql = "update Seller s set s.password=:pwd where s.email=:email";
			@SuppressWarnings("unchecked")
			Query query=em.createQuery(jpql);
			query.setParameter("pwd", seller.getPassword());//新密码
			query.setParameter("email", seller.getEmail());
			query.executeUpdate();
			
		}

		@Override
		public boolean sellerExist(String email) {
			String jpql="select s from Seller s where s.email=:email ";
			@SuppressWarnings("unchecked")
			List<Seller> list = em.createQuery(jpql)
					.setParameter("email", email)
					.getResultList();
			if(list.isEmpty())
				return false;
			else
			return true;
			
		}
}
