package com.koleksinaia.rest.controller.entity;

import com.koleksinaia.core.entity.Collection;
import com.koleksinaia.core.entity.Purchase;

public class PurchCollRequest {

	private Purchase purchase;
	
	private Collection collection;

	public Purchase getPurchase() {
		return purchase;
	}

	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}

	public Collection getCollection() {
		return collection;
	}

	public void setCollection(Collection collection) {
		this.collection = collection;
	}
	
	
}
