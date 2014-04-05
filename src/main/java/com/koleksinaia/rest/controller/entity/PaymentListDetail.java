package com.koleksinaia.rest.controller.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

import com.koleksinaia.core.entity.Payment;
import com.koleksinaia.rest.controller.response.SearchResult;

public class PaymentListDetail {

	private int id;
	
	private Date date;
	
	private String customerName;
	
	private int orderCount;
	
	private long orderPrice;
	
	private long shippingPrice;
	
	private long otherPrice;
	
	private long totalPrice;

	public PaymentListDetail(Payment payment) {
		this.id = payment.getId();
		this.date = payment.getDate();
		this.customerName = payment.getCustomer().getName();
		this.orderCount = payment.getOrderCount();
		this.orderPrice = payment.getOrderPrice();
		this.shippingPrice = payment.getShippingPrice();
		this.otherPrice = payment.getOtherPrice();
		this.totalPrice = payment.getTotalPrice();
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

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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
	
	public static List<PaymentListDetail> getPaymentList(List<Payment> payments) {
		List<PaymentListDetail> paymentList = new ArrayList<PaymentListDetail>();
		for (int i=0; i<payments.size(); i++) {			
			paymentList.add(new PaymentListDetail(payments.get(i)));
		}
		return paymentList;
	}
	
	public static SearchResult<PaymentListDetail> getPaymentSearchResult(Page<Payment> page) {
		SearchResult<PaymentListDetail> result = new SearchResult<PaymentListDetail>();
		result.setPageNumber(page.getNumber() + 1);
		result.setTotalItems(page.getTotalElements());
		result.setContent(PaymentListDetail.getPaymentList(page.getContent()));
		
		return result;
	}
}
