package com.koleksinaia.rest.controller.entity;

import java.util.ArrayList;
import java.util.List;

import com.koleksinaia.core.entity.helper.CustomerWithStatistic;

public class CustomerStatisticListDetail {

	private String customerId;
	
	private String customerName;
	
	private long statistic;

	public CustomerStatisticListDetail(CustomerWithStatistic customer) {
		this.customerId = customer.getCustomer().getId();
		this.customerName = customer.getCustomer().getName();
		this.statistic = customer.getStatistic();
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public long getStatistic() {
		return statistic;
	}

	public void setStatistic(long statistic) {
		this.statistic = statistic;
	}
	
	public static List<CustomerStatisticListDetail> getCustomerList(List<CustomerWithStatistic> customers) {
		List<CustomerStatisticListDetail> customerList = new ArrayList<CustomerStatisticListDetail>();
		for (int i=0; i<customers.size(); i++) {
			
			customerList.add(new CustomerStatisticListDetail(customers.get(i)));
		}
		return customerList;
	}
	
}
