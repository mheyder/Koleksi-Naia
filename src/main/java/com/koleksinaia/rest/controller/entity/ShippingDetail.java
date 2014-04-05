package com.koleksinaia.rest.controller.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.koleksinaia.core.entity.Order;
import com.koleksinaia.core.entity.Shipping;

public class ShippingDetail {

	private int id;
	
	private Date date;
	
	private int orderCount;
	
	private long price;
	
	private String dropship;
	
	private String address;
	
	private String info;
	
	private String customerId;
	
	private String customerName;	
	
	private boolean isPaid;
	
	private int paymentId;
	
	private Date paymentDate;
	
	private List<OrderListDetail> orders;

	public ShippingDetail(Shipping shipping) {
		this.id = shipping.getId();
		this.date = shipping.getDate();
		this.orderCount = shipping.getOrderCount();
		this.price = shipping.getPrice();
		this.dropship = shipping.getDropship();
		this.address = shipping.getAddress();
		this.info = shipping.getInfo();
		this.customerId = shipping.getCustomer().getId();
		this.customerName = shipping.getCustomer().getName();
		this.isPaid = shipping.getPayment() != null;
		if (isPaid) {
			this.paymentId = shipping.getPayment().getId();
			this.paymentDate = shipping.getPayment().getDate();
		}		
		this.orders = OrderListDetail.getOrderList(new ArrayList<Order>(shipping.getOrders()));
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

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public String getDropship() {
		return dropship;
	}

	public void setDropship(String dropship) {
		this.dropship = dropship;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
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

	public boolean isPaid() {
		return isPaid;
	}

	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
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

	public List<OrderListDetail> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderListDetail> orders) {
		this.orders = orders;
	}
	
	
}
