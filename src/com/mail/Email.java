package com.mail;

import java.util.ArrayList;
import java.util.List;

/**
 * 表示邮件类，你需要设置：账户名和密码、收件人、抄送(可选)、暗送(可选)、主题、内容，
 * 在创建了Mail对象之后
 * 可以调用它的setSubject()、setContent()，设置主题和正文
 * *也可以调用addAttch()添加附件
 * 也可以调用setFrom()和　addToAddress()，设置发件人，和添加收件人。
 */
public class Email {
	private String from;//发件人
	private StringBuilder toAddress = new StringBuilder();//收件人
	private StringBuilder ccAddress = new StringBuilder();//抄送
	private StringBuilder bccAddress = new StringBuilder();//暗送
	
	private String subject;//主题
	private String content;//正文
	
	// 附件列表
	private List<AttachBean> attachList = new ArrayList<AttachBean>();
		
	public Email() {}
	
	public Email(String from, String to) {
		this(from, to, null, null);
	}
	
	public Email(String from, String to, String subject, String content) {
		this.from = from;
		this.toAddress.append(to);
		this.subject = subject;
		this.content = content;
	}
	//返回发件人
	public void setFrom(String from) {
		this.from = from;
	}
	public String getFrom() {
		return from;
	}
	//返回收件人
	public String getToAddress() {
		return toAddress.toString();
	}

	public void setToAddress(StringBuilder toAddress) {
		this.toAddress = toAddress;
	}
	//返回抄送地址
	public String getCcAddress() {
		return ccAddress.toString();
	}

	public void setCcAddress(StringBuilder ccAddress) {
		this.ccAddress = ccAddress;
	}
	//返回暗送地址
	public String getBccAddress() {
		return bccAddress.toString();
	}

	public void setBccAddress(StringBuilder bccAddress) {
		this.bccAddress = bccAddress;
	}
	//设置主题
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	//设置发送内容
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	//添加多个发送地址
	public void addToAddress(String to){
		if(this.toAddress.length()>0){
			toAddress.append(";");
		}
		toAddress.append(to);
	}
	//添加多个抄送人
	public void addCcAddress(String cc) {
		if(this.ccAddress.length() > 0) {
			this.ccAddress.append(",");
		}
		this.ccAddress.append(cc);
	}
	//添加多个暗送人
	public void addBccAddress(String bcc) {
		if(this.bccAddress.length() > 0) {
			this.bccAddress.append(",");
		}
		this.bccAddress.append(bcc);
	}
	
	/**
	 * 添加附件，可以添加多个附件
	 * @param attachBean
	 */
	public void addAttach(AttachBean attachBean) {
		this.attachList.add(attachBean);
	}
	
	/**
	 * 获取所有附件
	 * @return
	 */
	public List<AttachBean> getAttachs() {
		return this.attachList;
	}
	
}
