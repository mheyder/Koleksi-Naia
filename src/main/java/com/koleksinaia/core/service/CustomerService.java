package com.koleksinaia.core.service;

import java.util.List;

import com.koleksinaia.core.entity.Customer;
import com.koleksinaia.core.entity.helper.CustomerWithStatistic;

public interface CustomerService {

	Customer findByCustomerId(String id); 
	
	boolean customerIdExists(String id);
	
	List<Customer> findAllCustomers();
	 
	Customer saveCustomer(Customer customer);
	    
	boolean deleteCustomer(String id);

	List<CustomerWithStatistic> findCustomersWithUnshippedOrders();

	List<CustomerWithStatistic> findCustomersWithUnpaidOrders();
}
