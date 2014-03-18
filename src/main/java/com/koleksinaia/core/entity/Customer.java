package com.koleksinaia.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "customer")
public class Customer {

	@Id
	@Column(name = "customer_id", unique = true)
	private String id;
	
	@Column(name = "customer_name")
	private String name;
	
	@Column(name = "customer_phone")
	private String phone;
	
	@Column(name = "customer_bbm")
	private String bbm;
	
	@Column(name = "customer_discount")
	private long discount;
	
	@Column(name = "customer_addr")
	private String address;
	
	@Column(name = "customer_info")
	private String info;
	
	public Customer() {
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getBbm() {
		return bbm;
	}
	public void setBbm(String bbm) {
		this.bbm = bbm;
	}
	public long getDiscount() {
		return discount;
	}
	public void setDiscount(long discount) {
		this.discount = discount;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
}
