package com.koleksinaia.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.koleksinaia.core.entity.Collection;
import com.koleksinaia.core.entity.Supplier;

public interface CollectionDao extends PagingAndSortingRepository<Collection, Integer> {

	@Query("SELECT c FROM com.koleksinaia.core.entity.Collection c JOIN FETCH c.orders WHERE c.id = (:id)")
    public Collection findByIdAndFetchOrdersEagerly(@Param("id") int id);
	
	//@Query("SELECT DISTINCT c FROM com.koleksinaia.core.entity.Collection c JOIN FETCH c.orders")
    //public List<Collection> findAllAndFetchOrders();
	
	Page<Collection> findByDateBetween(Date startDate, Date endDate, Pageable pageable);
	
	Page<Collection> findBySupplierAndDateBetween(Supplier supplier, Date startDate, Date endDate, Pageable pageable);
	
	@Query("SELECT c FROM com.koleksinaia.core.entity.Collection c WHERE c.supplier = :supplier AND c.purchase.id IS NULL AND c.cost > 0")
	List<Collection> findUnpurchasedCollectionsBySupplier(@Param("supplier") Supplier supplier);
}
 	