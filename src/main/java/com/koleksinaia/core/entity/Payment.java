package com.koleksinaia.core.entity;

import java.sql.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity(name = "payment")
public class Payment {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "payment_id", unique = true)
	private int id;
	
	@Column(name = "payment_date", nullable = false)
	private Date date;
	
	@Column(name = "payment_order_count", nullable = false)
	private int orderCount;
	
	@Column(name = "payment_order_price", nullable = false)
	private long orderPrice;
	
	@Column(name = "payment_shipping_price")
	private long shippingPrice;
	
	@Column(name = "payment_other_price")
	private long otherPrice;
	
	@Column(name = "payment_total_price")
	private long totalPrice;
	
	@Column(name = "payment_info")
	private String info;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;
	
	@OneToMany(mappedBy="payment", fetch = FetchType.LAZY)
	private Set<Order> orders;
	
	@OneToMany(mappedBy="payment", fetch = FetchType.LAZY)
	private Set<Shipping> shippings;
	
	public Payment() {
		
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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
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

	public Set<Shipping> getShippings() {
		return shippings;
	}

	public void setShippings(Set<Shipping> shippings) {
		this.shippings = shippings;
	}
	
	
}
