package com.koleksinaia.rest.controller.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.koleksinaia.core.entity.Order;
import com.koleksinaia.core.entity.Payment;
import com.koleksinaia.core.entity.Shipping;

public class PaymentDetail {

	private int id;
	
	private Date date;
	
	private int orderCount;
	
	private long orderPrice;
	
	private long shippingPrice;
	
	private long otherPrice;
	
	private long totalPrice;
	
	private String info;
	
	private String customerId;
	
	private String customerName;
	
	private List<OrderListDetail> orders;
	
	private List<ShippingListDetail> shippings;

	public PaymentDetail(Payment payment) {
		this.id = payment.getId();
		this.date = payment.getDate();
		this.orderCount = payment.getOrderCount();
		this.orderPrice = payment.getOrderPrice();
		this.shippingPrice = payment.getShippingPrice();
		this.otherPrice = payment.getOtherPrice();
		this.totalPrice = payment.getTotalPrice();
		this.info = payment.getInfo();
		this.customerId = payment.getCustomer().getId();
		this.customerName = payment.getCustomer().getName();
		this.orders = OrderListDetail.getOrderList(new ArrayList<Order>(payment.getOrders()));
		this.shippings = ShippingListDetail.getShippingList(new ArrayList<Shipping>(payment.getShippings()));
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

	public long getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(long orderPrice) {
		this.orderPrice = orderPrice;
	}

	public long getShippingPrice() {
		return shippingPrice;
	}

	public void setShippingPrice(long shippingPrice) {
		this.shippingPrice = shippingPrice;
	}

	public long getOtherPrice() {
		return otherPrice;
	}

	public void setOtherPrice(long otherPrice) {
		this.otherPrice = otherPrice;
	}

	public long getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(long totalPrice) {
		this.totalPrice = totalPrice;
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

	public List<OrderListDetail> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderListDetail> orders) {
		this.orders = orders;
	}

	public List<ShippingListDetail> getShippings() {
		return shippings;
	}

	public void setShippings(List<ShippingListDetail> shippings) {
		this.shippings = shippings;
	}
	
	
}
