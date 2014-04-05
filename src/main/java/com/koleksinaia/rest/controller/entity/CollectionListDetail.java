package com.koleksinaia.rest.controller.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

import com.koleksinaia.core.entity.Collection;
import com.koleksinaia.rest.controller.response.SearchResult;

public class CollectionListDetail {

	private int id;
	
	private Date date;
	
	private String supplierName;
	
	private int totalOrder;
	
	private long cost;
	
	private boolean isPaid;

	public CollectionListDetail(Collection collection) {
		this.id = collection.getId();
		this.date = collection.getDate();
		this.supplierName = collection.getSupplier().getName();
		this.totalOrder = collection.getTotalOrder();
		this.cost = collection.getCost();
		this.isPaid = (collection.getPurchase() != null);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public int getTotalOrder() {
		return totalOrder;
	}

	public void setTotalOrder(int totalOrder) {
		this.totalOrder = totalOrder;
	}

	public long getCost() {
		return cost;
	}

	public void setCost(long cost) {
		this.cost = cost;
	}

	public boolean isPaid() {
		return isPaid;
	}

	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}
	
	public static List<CollectionListDetail> getCollectionList(List<Collection> collections) {
		List<CollectionListDetail> collectionList = new ArrayList<CollectionListDetail>();
		for (int i=0; i<collections.size(); i++) {			
			collectionList.add(new CollectionListDetail(collections.get(i)));
		}
		return collectionList;
	}
	
	public static SearchResult<CollectionListDetail> getCollectionSearchResult(Page<Collection> page) {
		SearchResult<CollectionListDetail> result = new SearchResult<CollectionListDetail>();
		result.setPageNumber(page.getNumber() + 1);
		result.setTotalItems(page.getTotalElements());
		result.setContent(CollectionListDetail.getCollectionList(page.getContent()));
		
		return result;
	}
}
