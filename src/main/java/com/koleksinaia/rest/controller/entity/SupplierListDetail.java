package com.koleksinaia.rest.controller.entity;

import java.util.ArrayList;
import java.util.List;

import com.koleksinaia.core.entity.Supplier;

public class SupplierListDetail {

	private String id;
	
	private String name;
	
	private String contactPerson;
	
	private String phone;
	
	private String bbm;

	public SupplierListDetail(Supplier supplier) {
		this.id = supplier.getId();
		this.name = supplier.getName();
		this.contactPerson = supplier.getContactPerson();
		this.phone = supplier.getPhone();
		this.bbm = supplier.getBbm();
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

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBbm() {
		return bbm;
	}

	public void setBbm(String bbm) {
		this.bbm = bbm;
	}
	
	public static List<SupplierListDetail> getSupplierList(List<Supplier> suppliers) {
		List<SupplierListDetail> supplierList = new ArrayList<SupplierListDetail>();
		for (int i=0; i<suppliers.size(); i++) {
			supplierList.add(new SupplierListDetail(suppliers.get(i)));
		}
		return supplierList;
	}
}
