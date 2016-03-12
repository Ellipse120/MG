package com.mg.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="sellers")
public class Seller implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(length=60,nullable=false)
	private Integer sellerId;
	@Column(length=60,nullable=false)
	private String sellerName;//商家名称
	@Column(length=60,nullable=false)
	private String email;    //商家注册邮箱
	@Column(length=60,nullable=false)
	private String password;
	@Column(length=60,nullable=false)
	private String phone;
	@Column(length=300,nullable=false)
	private String address;
	private Date date;//商家注册时间
	@Column(length=200,nullable=false)
	private String type;    //商家经营类型
	@Column(length=300,nullable=false)
	private String licencefile;//商家营业执照
	
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getTypes() {
		return type;
	}
	public void setTypes(String types) {
		this.type = types;
	}
	public String getLicencefile() {
		return licencefile;
	}
	public void setLicencefile(String licencefile) {
		this.licencefile = licencefile;
	}
	public Integer getSellerId() {
		return sellerId;
	}
	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString(){
		return sellerName+"{"+email+" "+password+" "+phone+" "+address+" "+
				date+" "+type+" "+licencefile+"}";
		
	}

}
