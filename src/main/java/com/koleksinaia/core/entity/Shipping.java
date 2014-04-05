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

@Entity(name = "shipping")
public class Shipping {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "shipping_id", unique = true)
	private int id;
	
	@Column(name = "shipping_date", nullable = false)
	private Date date;
	
	@Column(name = "shipping_order_count")
	private int orderCount;
	
	@Column(name = "shipping_price")
	private long price;
	
	@Column(name = "shipping_dropship")
	private String dropship;
	
	@Column(name = "shipping_addr")
	private String address;
	
	@Column(name = "shipping_info")
	private String info;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "payment_id")
	private Payment payment;
	
	@OneToMany(mappedBy="shipping", fetch = FetchType.LAZY)
	private Set<Order> orders;
	
	public Shipping() {
		
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

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public String getDropship() {
		return dropship;
	}

	public void setDropship(String dropship) {
		this.dropship = dropship;
	}
	
	
}
