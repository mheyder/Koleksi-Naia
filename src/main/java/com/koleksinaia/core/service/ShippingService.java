package com.koleksinaia.core.service;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import com.koleksinaia.core.entity.Shipping;


public interface ShippingService {

	Shipping findByShippingId(int id);
	
	boolean shippingIdExists(int id);
	
	Page<Shipping> findShippings(int page, String sortDirection, Date startDate, Date endDate, String customerId);
	 
	Shipping saveShipping(Shipping shipping);
	    
	boolean deleteShipping(String id);

	List<Shipping> findUnpaidShippingsByCustomer(String customerId);
}
