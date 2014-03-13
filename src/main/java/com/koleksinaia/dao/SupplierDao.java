package com.koleksinaia.dao;

import org.springframework.data.repository.CrudRepository;

import com.koleksinaia.core.entity.Supplier;

public interface SupplierDao extends CrudRepository<Supplier, String> {
	
}
