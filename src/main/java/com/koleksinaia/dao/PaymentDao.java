package com.koleksinaia.dao;

import java.sql.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.koleksinaia.core.entity.Customer;
import com.koleksinaia.core.entity.Payment;

public interface PaymentDao extends CrudRepository<Payment, Integer>{
	
	public Page<Payment> findByDateBetween(Date startDateRange, Date endDateRange, Pageable pageable);

	public Page<Payment> findByCustomerAndDateBetween(Customer customer, Date startDateRange, Date endDateRange, Pageable pageable);

}
