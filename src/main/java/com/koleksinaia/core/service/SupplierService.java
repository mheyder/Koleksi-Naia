package com.koleksinaia.core.service;

import java.util.List;

import com.koleksinaia.core.entity.Supplier;

public interface SupplierService {

	Supplier findBySupplierId(String id); 
	
	boolean supplierIdExists(String id);
	
	List<Supplier> findAllSuppliers();
	 
	Supplier saveSupplier(Supplier supplier);
	    
	boolean deleteSupplier(String id);
}
