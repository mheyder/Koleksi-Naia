package com.koleksinaia.rest.controller.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

import com.koleksinaia.core.entity.Purchase;
import com.koleksinaia.rest.controller.response.SearchResult;

public class PurchaseListDetail {

	private int id;
	
	private Date date;
	
	private String supplierName;
	
	private int orderCount;
	
	private long orderCost;
	
	private long collectionCost;
	
	private long otherCost;
	
	private long totalCost;	
	

	public PurchaseListDetail(Purchase purchase) {
		this.id = purchase.getId();
		this.date = purchase.getDate();
		this.supplierName = purchase.getSupplier().getName();
		this.orderCount = purchase.getOrderCount();
		this.orderCost = purchase.getOrderCost();
		this.collectionCost = purchase.getCollectionCost();
		this.otherCost = purchase.getOtherCost();
		this.totalCost = purchase.getTotalCost();		
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

	public long getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(long totalCost) {
		this.totalCost = totalCost;
	}

	public int getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}	

	public long getOrderCost() {
		return orderCost;
	}

	public void setOrderCost(long orderCost) {
		this.orderCost = orderCost;
	}

	public long getCollectionCost() {
		return collectionCost;
	}

	public void setCollectionCost(long collectionCost) {
		this.collectionCost = collectionCost;
	}

	public long getOtherCost() {
		return otherCost;
	}

	public void setOtherCost(long otherCost) {
		this.otherCost = otherCost;
	}

	public static List<PurchaseListDetail> getPurchaseList(List<Purchase> purchases) {
		List<PurchaseListDetail> purchaseList = new ArrayList<PurchaseListDetail>();
		for (int i=0; i<purchases.size(); i++) {			
			purchaseList.add(new PurchaseListDetail(purchases.get(i)));
		}
		return purchaseList;
	}
	
	public static SearchResult<PurchaseListDetail> getPurchaseSearchResult(Page<Purchase> page) {
		SearchResult<PurchaseListDetail> result = new SearchResult<PurchaseListDetail>();
		result.setPageNumber(page.getNumber() + 1);
		result.setTotalItems(page.getTotalElements());
		result.setContent(PurchaseListDetail.getPurchaseList(page.getContent()));
		
		return result;
	}
}
