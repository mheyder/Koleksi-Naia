package com.koleksinaia.core.entity.helper;

import com.koleksinaia.core.entity.Supplier;

public class SuppplierWithStatistic {

	private Supplier supplier;
	
	private long statistic;

	public SuppplierWithStatistic(Supplier supplier, long statistic) {
		this.supplier = supplier;
		this.statistic = statistic;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public long getStatistic() {
		return statistic;
	}

	public void setStatistic(long statistic) {
		this.statistic = statistic;
	}
	
	
}
