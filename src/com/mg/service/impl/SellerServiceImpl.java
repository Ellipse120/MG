package com.mg.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mg.dao.SellerDao;
import com.mg.service.SellerService;
import com.mg.vo.Seller;

	@Service("si")
	public class SellerServiceImpl implements SellerService {
		@Autowired(required=true)
		private SellerDao sd;

		@Override
		public Seller login(String sellerName, String password) {
			return sd.querySeller(sellerName, password);
		}

		@Override
		@Transactional
		public int regist(Seller seller) {
			return sd.addSeller(seller);
		}
}
