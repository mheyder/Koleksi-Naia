package com.koleksinaia.core.service;

import java.util.List;

import com.koleksinaia.core.entity.Supplier;
import com.koleksinaia.core.entity.helper.SuppplierWithStatistic;

public interface SupplierService {

	Supplier findBySupplierId(String id); 
	
	boolean supplierIdExists(String id);
	
	List<Supplier> findAllSuppliers();
	 
	Supplier saveSupplier(Supplier supplier);
	    
	boolean deleteSupplier(String id);
	
	List<SuppplierWithStatistic> findSuppliersWithUncollectedOrders();

	List<SuppplierWithStatistic> findSuppliersWithUnpurchasedOrders();
}
