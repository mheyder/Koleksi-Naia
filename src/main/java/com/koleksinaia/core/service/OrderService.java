package com.koleksinaia.core.service;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import com.koleksinaia.core.entity.Order;

public interface OrderService {

	Order findByOrderId(int id); 
	
	boolean orderIdExists(int id);
	
	List<Order> findAllOrders();
	
	Page<Order> customSearch(int page, String sortDirection, Date startDate, Date endDate, String customerId, String supplierId);
	
	Order saveOrder(Order order);
	 
	boolean saveOrders(List<Order> orders);
	    
	boolean deleteOrder(int id);
	
	List<Order> findAllActiveOrdersBySupplier(String supplierId);
	
	List<Order> findUncollectedOrdersBySupplier(String supplierId);

	List<Order> findUnpurchasedOrdersBySupplier(String supplierId);

	List<Order> findUnshippedOrdersOfCustomer(String customerId);

	List<Order> findUnpaidOrdersOfCustomer(String customerId);
}
