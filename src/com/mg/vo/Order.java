package com.mg.vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="orders")
public class Order {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer orderId;
	private String orderNum;//订单
	private String orderInfo;//订单信息
	private int amount;//购买数量
	private Double settle;//总价
	private boolean orderStatus;//订单状态
	private String address;//收货地址
	private User user;
	private String phone;
	private String uName;
	
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getuName() {
		return uName;
	}
	public void setuName(String uName) {
		this.uName = uName;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getOrderInfo() {
		return orderInfo;
	}
	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public Double getSettle() {
		return settle;
	}
	public void setSettle(Double settle) {
		this.settle = settle;
	}
	public boolean isOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(boolean orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
	
}
