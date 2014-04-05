package com.koleksinaia.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.koleksinaia.core.entity.Customer;
import com.koleksinaia.core.entity.Shipping;

public interface ShippingDao extends CrudRepository<Shipping, Integer> {

	@Query("SELECT DISTINCT s FROM com.koleksinaia.core.entity.Shipping s JOIN FETCH s.orders")
    public List<Shipping> findAllAndFetchOrders();
	
	@Query("SELECT s FROM com.koleksinaia.core.entity.Shipping s JOIN FETCH s.orders WHERE s.id = (:id)")
    public Shipping findByIdAndFetchOrdersEagerly(@Param("id") int id);
	
	Page<Shipping> findByDateBetween(Date startDate, Date endDate, Pageable pageable);
	
	Page<Shipping> findByCustomerAndDateBetween(Customer customer, Date startDate, Date endDate, Pageable pageable);
	
	@Query("SELECT s FROM com.koleksinaia.core.entity.Shipping s WHERE s.customer = :customer AND s.payment.id IS NULL AND s.price > 0")
	List<Shipping> findUnpaidShippingByCustomer(@Param("customer") Customer customer);
}
