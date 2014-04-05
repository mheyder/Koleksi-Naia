package com.koleksinaia.core.entity.helper;

import com.koleksinaia.core.entity.Customer;

public class CustomerWithStatistic {

	private Customer customer;
	
	private long statistic;

	public CustomerWithStatistic(Customer customer, long statistic) {
		this.customer = customer;
		this.statistic = statistic;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public long getStatistic() {
		return statistic;
	}

	public void setStatistic(long statistic) {
		this.statistic = statistic;
	}
		
}
