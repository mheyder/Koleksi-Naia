package com.koleksinaia.rest.controller.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.koleksinaia.core.entity.Collection;
import com.koleksinaia.core.entity.Order;
import com.koleksinaia.core.entity.Purchase;

public class PurchaseDetail {

	private int id;
	
	private Date date;	
	
	private int orderCount;
	
	private long orderCost;
	
	private long collectionCost;
	
	private long otherCost;
	
	private long totalCost;
	
	private String info;
	
	private String supplierId;
	
	private String supplierName;
	
	private List<OrderListDetail> orders;
	
	private List<CollectionListDetail> collections;

	public PurchaseDetail(Purchase purchase) {
		this.id = purchase.getId();
		this.date = purchase.getDate();
		this.orderCount = purchase.getOrderCount();
		this.orderCost = purchase.getOrderCost();
		this.collectionCost = purchase.getCollectionCost();
		this.otherCost = purchase.getOtherCost();
		this.totalCost = purchase.getTotalCost();
		this.info = purchase.getInfo();
		this.supplierId = purchase.getSupplier().getId();
		this.supplierName = purchase.getSupplier().getName();
		this.orders = OrderListDetail.getOrderList(new ArrayList<Order>(purchase.getOrders()));
		this.collections = CollectionListDetail.getCollectionList(new ArrayList<Collection>(purchase.getCollections()));
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

	public long getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(long totalCost) {
		this.totalCost = totalCost;
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

	public List<OrderListDetail> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderListDetail> orders) {
		this.orders = orders;
	}

	public List<CollectionListDetail> getCollections() {
		return collections;
	}

	public void setCollections(List<CollectionListDetail> collections) {
		this.collections = collections;
	}
	
	
}
