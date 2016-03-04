package com.mg.dao;

import com.mg.vo.Seller;

public interface SellerDao {
	public int addSeller(Seller seller);

	public Seller querySeller(String sellerName, String password);
}
