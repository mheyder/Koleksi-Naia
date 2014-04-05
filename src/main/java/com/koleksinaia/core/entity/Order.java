package com.koleksinaia.core.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity(name = "myorder")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "order_id", unique=true, nullable=false)
	private int id;
	
	@Column(name = "order_date", nullable = false)
	private Date date;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade({CascadeType.ALL})
	@JoinColumn(name = "customer_id")
	private Customer customer; 
	
	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade({CascadeType.ALL})
	@JoinColumn(name = "supplier_id")
	private Supplier supplier;
	
	@Column(name = "order_dropship")
	private String dropship;
	
	@Column(name = "order_item_name", nullable = false)
	private String itemName;
	
	@Column(name = "order_item_type")
	private String itemType;
	
	@Column(name = "order_cost", nullable = false)
	private long cost;
	
	@Column(name = "order_price", nullable = false)
	private long price;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "purchase_id")
	private Purchase purchase;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "collection_id")
	private Collection collection;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "payment_id")
	private Payment payment;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "shipping_id")
	private Shipping shipping;
	
	public Order() {
		
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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
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

	public Purchase getPurchase() {
		return purchase;
	}

	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}

	public Collection getCollection() {
		return collection;
	}

	public void setCollection(Collection collection) {
		this.collection = collection;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public Shipping getShipping() {
		return shipping;
	}

	public void setShipping(Shipping shipping) {
		this.shipping = shipping;
	}
	
}
