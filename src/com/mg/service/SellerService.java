package com.mg.service;

import com.mg.vo.Seller;

public interface SellerService {
	public Seller login(String sellerName, String password);

	public int regist(Seller seller);

	public boolean verify(Seller seller,String sendCode, String verifyCode);

	public void updatePwd(Seller seller);
}
