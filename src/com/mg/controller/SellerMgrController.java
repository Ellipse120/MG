package com.mg.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mg.service.SellerService;
import com.mg.vo.Seller;

@Controller
public class SellerMgrController {
	@Autowired(required = true)
	private SellerService ss;

	@RequestMapping("common/sellerLogin")
	public String login(String sellerName, String password,
			HttpServletRequest request) {
		System.out.println(sellerName);
		System.out.println(password);
		
		Seller seller = ss.login(sellerName, password);
		System.out.println(seller);
		if (seller != null) {
			request.getSession().setAttribute("sellerName", sellerName);
			return "redirect:/registSuc.html";
		} else {
			return "common/sellerLogin";
		}
	}

	@RequestMapping("common/sellerRegist")
	public String regist(Seller seller, HttpServletRequest request) {
		System.out.println(seller.getSellerName());
		int flag = ss.regist(seller);
		if (flag > 0) {
			return "redirect:/registSuc.html";
		} else {
			return "common/sellerLogin";
		}
	}
}
