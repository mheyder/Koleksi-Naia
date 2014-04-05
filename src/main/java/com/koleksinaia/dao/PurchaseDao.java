package com.koleksinaia.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.koleksinaia.core.entity.Purchase;
import com.koleksinaia.core.entity.Supplier;

public interface PurchaseDao extends CrudRepository<Purchase, Integer> {

	@Query("SELECT DISTINCT p FROM com.koleksinaia.core.entity.Purchase p JOIN FETCH p.orders")
    public List<Purchase> findAllAndFetchOrders();
		
	Page<Purchase> findByDateBetween(Date startDate, Date endDate, Pageable pageable);
	
	Page<Purchase> findBySupplierAndDateBetween(Supplier supplier, Date startDate, Date endDate, Pageable pageable);
}
