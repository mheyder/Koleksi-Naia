package com.koleksinaia.core.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.koleksinaia.core.entity.Order;


public interface OrderService {

	Order findByOrderId(int id); 
	
	boolean orderIdExists(int id);
	
	List<Order> findAllOrders();
	
	Page<Order> customSearch();
	
	Order saveOrder(Order order);
	 
	boolean saveOrders(List<Order> orders);
	    
	boolean deleteOrder(int id);
}
