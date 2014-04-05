package com.koleksinaia.rest.controller.entity;

import java.util.ArrayList;
import java.util.List;

import com.koleksinaia.core.entity.helper.SuppplierWithStatistic;

public class SupplierStatisticListDetail {

	private String supplierId;
	
	private String supplierName;
	
	private long statistic;

	public SupplierStatisticListDetail(SuppplierWithStatistic supplier) {
		this.supplierId = supplier.getSupplier().getId();
		this.supplierName = supplier.getSupplier().getName();
		this.statistic = supplier.getStatistic();
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public long getStatistic() {
		return statistic;
	}

	public void setStatistic(long statistic) {
		this.statistic = statistic;
	}
	
	public static List<SupplierStatisticListDetail> getSupplierList(List<SuppplierWithStatistic> suppliers) {
		List<SupplierStatisticListDetail> supplierList = new ArrayList<SupplierStatisticListDetail>();
		for (int i=0; i<suppliers.size(); i++) {
			
			supplierList.add(new SupplierStatisticListDetail(suppliers.get(i)));
		}
		return supplierList;
	}
}
