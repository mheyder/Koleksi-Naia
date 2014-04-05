package com.koleksinaia.rest.controller.entity;

import java.util.ArrayList;
import java.util.List;

import com.koleksinaia.core.entity.Customer;

public class CustomerNameListDetail {

	private String id;
	
	private String name;
	
	private long discount;
	
	public CustomerNameListDetail (Customer customer) {
		this.id = customer.getId();
		this.name = customer.getName();
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

	public long getDiscount() {
		return discount;
	}

	public void setDiscount(long discount) {
		this.discount = discount;
	}
	
	public static List<CustomerNameListDetail> getCustomerList(List<Customer> customers) {
		List<CustomerNameListDetail> customerNameList = new ArrayList<CustomerNameListDetail>();
		for (int i=0; i<customers.size(); i++) {
			
			customerNameList.add(new CustomerNameListDetail(customers.get(i)));
		}
		return customerNameList;
	}
}
