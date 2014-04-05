package com.koleksinaia.rest.controller.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

import com.koleksinaia.core.entity.Order;
import com.koleksinaia.rest.controller.response.SearchResult;

public class OrderListDetail {

	private int id;
	
	private Date date;
	
	private String customerId;
	
	private String supplierId;
	
	private String dropship;
	
	private String itemName;
	
	private String itemType;
	
	private long cost;
	
	private long price;
	
	private boolean isPurchased;
	
	private boolean isCollected;
	
	private boolean isPaid;
	
	private boolean isShipped;
	
	

	public OrderListDetail(Order order) {
		this.id = order.getId();
		this.date = order.getDate();
		this.customerId = order.getCustomer().getId();
		this.supplierId = order.getSupplier().getId();
		this.dropship = order.getDropship();
		this.itemName = order.getItemName();
		this.itemType = order.getItemType();
		this.cost = order.getCost();
		this.price = order.getPrice();
		this.isPurchased = (order.getPurchase() != null);
		this.isCollected = (order.getCollection() != null);
		this.isPaid = (order.getPayment() != null);
		this.isShipped = (order.getShipping() != null);
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

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
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

	public boolean isPaid() {
		return isPaid;
	}

	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}

	public boolean isShipped() {
		return isShipped;
	}

	public void setShipped(boolean isShipped) {
		this.isShipped = isShipped;
	}
	
	public static List<OrderListDetail> getOrderList(List<Order> orders) {
		List<OrderListDetail> orderList = new ArrayList<OrderListDetail>();
		for (int i=0; i<orders.size(); i++) {
			orderList.add(new OrderListDetail(orders.get(i)));
		}
		return orderList;
	}
	
	public static SearchResult<OrderListDetail> getOrderSearchResult(Page<Order> page) {
		SearchResult<OrderListDetail> result = new SearchResult<OrderListDetail>();
		result.setPageNumber(page.getNumber() + 1);
		result.setTotalItems(page.getTotalElements());
		result.setContent(OrderListDetail.getOrderList(page.getContent()));
		
		return result;
	}
}
