package com.koleksinaia.rest.controller.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.koleksinaia.core.entity.Order;

public class ActiveOrderSupplierDetail {

	private int id;
	
	private Date date;
	
	private String customerId;
	
	private String dropship;
	
	private String itemName;
	
	private String itemType;
	
	private long cost;
	
	private long price;
	
	private boolean isPurchased;
	
	private boolean isCollected;

	public ActiveOrderSupplierDetail(Order order) {
		this.id = order.getId();
		this.date = order.getDate();
		this.customerId = order.getCustomer().getId();
		this.dropship = order.getDropship();
		this.itemName = order.getItemName();
		this.itemType = order.getItemType();
		this.cost = order.getCost();
		this.price = order.getPrice();
		this.isPurchased = (order.getPurchase() != null);
		this.isCollected = (order.getCollection() != null);
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

	public boolean isPurchased() {
		return isPurchased;
	}

	public void setPurchased(boolean isPurchased) {
		this.isPurchased = isPurchased;
	}

	public boolean isCollected() {
		return isCollected;
	}

	public void setCollected(boolean isCollected) {
		this.isCollected = isCollected;
	}
	
	public static List<ActiveOrderSupplierDetail> getOrderList(List<Order> orders) {
		List<ActiveOrderSupplierDetail> orderList = new ArrayList<ActiveOrderSupplierDetail>();
		for (int i=0; i<orders.size(); i++) {
			orderList.add(new ActiveOrderSupplierDetail(orders.get(i)));
		}
		return orderList;
	}
}
