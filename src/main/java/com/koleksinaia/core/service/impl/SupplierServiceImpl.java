package com.koleksinaia.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.koleksinaia.core.entity.Supplier;
import com.koleksinaia.core.service.SupplierService;
import com.koleksinaia.dao.SupplierDao;


@Service("supplierService")
@Transactional(readOnly = true)
public class SupplierServiceImpl implements SupplierService {
	
	@Resource
	private SupplierDao supplierDao;

	@Override
	public Supplier findBySupplierId(String id) {
		Supplier suppliers = supplierDao.findOne(id);
		
		return suppliers;
	}

	@Override
	public List<Supplier> findAllSuppliers() {
		return (List<Supplier>) supplierDao.findAll();
	}

	@Override
	@Transactional(readOnly = false)
	public Supplier saveSupplier(Supplier supplier) {
		return supplierDao.save(supplier);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean deleteSupplier(String id) {
		if (supplierDao.exists(id)) {
			supplierDao.delete(id);
			return !supplierDao.exists(id);
		}
		return false;
	}

	@Override
	public boolean supplierIdExists(String id) {
		return supplierDao.exists(id);
	}

}
