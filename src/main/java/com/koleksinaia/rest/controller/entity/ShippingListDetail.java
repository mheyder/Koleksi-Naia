package com.koleksinaia.rest.controller.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

import com.koleksinaia.core.entity.Shipping;
import com.koleksinaia.rest.controller.response.SearchResult;

public class ShippingListDetail {

	private int id;
	
	private Date date;
	
	private String customerName;
	
	private String dropship;
	
	private int orderCount;
	
	private long price;
	
	private boolean isPaid;

	public ShippingListDetail(Shipping shipping) {
		this.id = shipping.getId();
		this.date = shipping.getDate();
		this.customerName = shipping.getCustomer().getName();
		this.dropship = shipping.getDropship();
		this.orderCount = shipping.getOrderCount();
		this.price = shipping.getPrice();
		this.isPaid = shipping.getPayment() != null;
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

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public boolean isPaid() {
		return isPaid;
	}

	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}

	public String getDropship() {
		return dropship;
	}

	public void setDropship(String dropship) {
		this.dropship = dropship;
	}	
	
	public static List<ShippingListDetail> getShippingList(List<Shipping> shippings) {
		List<ShippingListDetail> shippingList = new ArrayList<ShippingListDetail>();
		for (int i=0; i<shippings.size(); i++) {			
			shippingList.add(new ShippingListDetail(shippings.get(i)));
		}
		return shippingList;
	}
	
	public static SearchResult<ShippingListDetail> getShippingSearchResult(Page<Shipping> page) {
		SearchResult<ShippingListDetail> result = new SearchResult<ShippingListDetail>();
		result.setPageNumber(page.getNumber() + 1);
		result.setTotalItems(page.getTotalElements());
		result.setContent(ShippingListDetail.getShippingList(page.getContent()));
		
		return result;
	}
	
}
