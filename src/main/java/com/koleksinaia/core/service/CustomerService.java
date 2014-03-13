package com.koleksinaia.core.service;

import java.util.List;

import com.koleksinaia.core.entity.Customer;


public interface CustomerService {

	Customer findByCustomerId(String id); 
	
	boolean customerIdExists(String id);
	
	List<Customer> findAllCustomers();
	 
	Customer saveCustomer(Customer customer);
	    
	boolean deleteCustomer(String id);
}
