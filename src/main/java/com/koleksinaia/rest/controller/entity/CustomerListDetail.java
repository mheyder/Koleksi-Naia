package com.koleksinaia.rest.controller.entity;

import java.util.ArrayList;
import java.util.List;

import com.koleksinaia.core.entity.Customer;

public class CustomerListDetail {

	private String id;
	
	private String name;
	
	private String phone;
	
	private String bbm;
	
	private long discount;
	
	public CustomerListDetail (Customer customer) {
		this.id = customer.getId();
		this.name = customer.getName();
		this.phone = customer.getPhone();
		this.bbm = customer.getBbm();
		this.discount = customer.getDiscount();
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
	
	public static List<CustomerListDetail> getCustomerList(List<Customer> customers) {
		List<CustomerListDetail> customerList = new ArrayList<CustomerListDetail>();
		for (int i=0; i<customers.size(); i++) {
			
			customerList.add(new CustomerListDetail(customers.get(i)));
		}
		return customerList;
	}
}
