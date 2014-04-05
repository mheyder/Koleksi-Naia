package com.koleksinaia.rest.controller.entity;

import java.sql.Date;
import com.koleksinaia.core.entity.Order;

public class OrderDetail {

	private int id;
	
	private Date date;
	
	private String customerId;
	
	private String customerName;

	private String supplierId;
	
	private String supplierName;
	
	private String dropship;
	
	private String itemName;
	
	private String itemType;
	
	private long cost;
	
	private long price;
	
	private int purchaseId;
	
	private Date purchaseDate;

	private int collectionId;
	
	private Date collectionDate;

	private int paymentId;
	
	private Date paymentDate;

	private int shippingId;
	
	private Date shippingDate;

	public OrderDetail(Order order) {
		this.id = order.getId();
		this.date = order.getDate();
		this.customerId = order.getCustomer().getId();
		this.customerName = order.getCustomer().getName();
		this.supplierId = order.getSupplier().getId();
		this.supplierName = order.getSupplier().getName();
		this.dropship = order.getDropship();
		this.itemName = order.getItemName();
		this.itemType = order.getItemType();
		this.cost = order.getCost();
		this.price = order.getPrice();
		this.purchaseId = order.getPurchase().getId();
		this.purchaseDate = order.getPurchase().getDate();
		this.collectionId = order.getCollection().getId();
		this.collectionDate = order.getCollection().getDate();
		this.paymentId = order.getPayment().getId();
		this.paymentDate = order.getPayment().getDate();
		this.shippingId = order.getShipping().getId();
		this.shippingDate = order.getShipping().getDate();
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

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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

	public String getDropship() {
		return dropship;
	}

	public void setDropship(String dropship) {
		this.dropship = dropship;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public long getCost() {
		return cost;
	}

	public void setCost(long cost) {
		this.cost = cost;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
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

	public int getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(int collectionId) {
		this.collectionId = collectionId;
	}

	public Date getCollectionDate() {
		return collectionDate;
	}

	public void setCollectionDate(Date collectionDate) {
		this.collectionDate = collectionDate;
	}

	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public int getShippingId() {
		return shippingId;
	}

	public void setShippingId(int shippingId) {
		this.shippingId = shippingId;
	}

	public Date getShippingDate() {
		return shippingDate;
	}

	public void setShippingDate(Date shippingDate) {
		this.shippingDate = shippingDate;
	}
	
	
}
