package com.koleksinaia.rest.controller.entity;

import java.util.ArrayList;
import java.util.List;

import com.koleksinaia.core.entity.Supplier;

public class SupplierNameListDetail {
	
	private String id;
	
	private String name;

	public SupplierNameListDetail(Supplier supplier) {
		this.id = supplier.getId();
		this.name = supplier.getName();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public static List<SupplierNameListDetail> getSupplierList(List<Supplier> suppliers) {
		List<SupplierNameListDetail> supplierNameList = new ArrayList<SupplierNameListDetail>();
		for (int i=0; i<suppliers.size(); i++) {
			supplierNameList.add(new SupplierNameListDetail(suppliers.get(i)));
		}
		return supplierNameList;
	}

}
