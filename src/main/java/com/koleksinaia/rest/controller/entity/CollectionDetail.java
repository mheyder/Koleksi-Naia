package com.koleksinaia.rest.controller.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.koleksinaia.core.entity.Collection;
import com.koleksinaia.core.entity.Order;

public class CollectionDetail {

	private int id;
	
	private Date date;
	
	private int totalOrder;
	
	private long cost;
	
	private String info;
	
	private String supplierId;
	
	private String supplierName;	
	
	private boolean isPaid;
	
	private int purchaseId;
	
	private Date purchaseDate;
	
	private List<OrderListDetail> orders;

	public CollectionDetail(Collection collection) {
		this.id = collection.getId();
		this.date = collection.getDate();		
		this.totalOrder = collection.getTotalOrder();
		this.cost = collection.getCost();
		this.info = collection.getInfo();
		this.supplierId = collection.getSupplier().getId();
		this.supplierName = collection.getSupplier().getName();
		this.isPaid = (collection.getPurchase() != null);
		if (isPaid) {
			this.purchaseId = collection.getPurchase().getId();
			this.purchaseDate = collection.getPurchase().getDate();
		}		
		this.orders = OrderListDetail.getOrderList(new ArrayList<Order>(collection.getOrders()));
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

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
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

	public boolean isPaid() {
		return isPaid;
	}

	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}

	public int getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(int purchaseId) {
		this.purchaseId = purchaseId;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public List<OrderListDetail> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderListDetail> orders) {
		this.orders = orders;
	}
	
	
}
