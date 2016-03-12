package com.mg.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.mg.service.SellerService;
import com.mg.util.CommonUtil;
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
			HttpSession session = request.getSession();
			session.setAttribute("sellerName", sellerName);
			return "redirect:/registSuc.html";
		} else {
			return "common/sellerLogin";
		}
	}

	@RequestMapping(value="common/sellerRegist",method=RequestMethod.POST)
	public String regist(Seller seller,HttpServletRequest request) throws Exception {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		//获得解析器
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> items = upload.parseRequest(request);
		/*
		 * 先将普通表单字段封装到Map中
		 * 再把Map中的数据封装到Seller对象中
		 */
		//封装普通数据到Seller对象中之后进行文件路径的保存和文件的自身的上传
		Map<String , String> map = new HashMap<String,String>();
		for (FileItem fileItem : items) {
			if(fileItem.isFormField()){
				map.put(fileItem.getFieldName(), fileItem.getString("utf-8"));
			}
		}
		seller = CommonUtil.toBean(map, Seller.class);
		
		String licencefile_name = items.get(2).getName();
		String licenecfile_path = request.getServletContext().getRealPath("/uploadLicence");
		seller.setLicencefile(licencefile_name);
		File licenec_file = new File(licenecfile_path,licencefile_name);
		items.get(2).write(licenec_file);
		System.out.println(seller);
		
		int flag = ss.regist(seller);
		if (flag > 0) {
			return "redirect:/registSuc.html";
		} else {
			return "common/sellerLogin";
		}
	}
}
