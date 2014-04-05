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

@Entity(name = "purchase")
public class Purchase {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "purchase_id", unique = true)
	private int id;
	
	@Column(name = "purchase_date", nullable = false)
	private Date date;	
	
	@Column(name = "purchase_order_count", nullable = false)
	private int orderCount;
	
	@Column(name = "purchase_order_cost", nullable = false)
	private long orderCost;
	
	@Column(name = "purchase_collection_cost")
	private long collectionCost;
	
	@Column(name = "purchase_other_cost")
	private long otherCost;
	
	@Column(name = "purchase_total_cost")
	private long totalCost;
	
	@Column(name = "purchase_info")
	private String info;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "supplier_id", nullable = false)
	private Supplier supplier;
	
	@OneToMany(mappedBy="purchase", fetch = FetchType.LAZY)
	private Set<Order> orders;
	
	@OneToMany(mappedBy="purchase", fetch = FetchType.LAZY)
	private Set<Collection> collections;
	
	public Purchase() {
		
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

	public long getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(long totalCost) {
		this.totalCost = totalCost;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
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

	public long getOrderCost() {
		return orderCost;
	}

	public void setOrderCost(long orderCost) {
		this.orderCost = orderCost;
	}

	public long getCollectionCost() {
		return collectionCost;
	}

	public void setCollectionCost(long collectionCost) {
		this.collectionCost = collectionCost;
	}

	public long getOtherCost() {
		return otherCost;
	}

	public void setOtherCost(long otherCost) {
		this.otherCost = otherCost;
	}

	public Set<Collection> getCollections() {
		return collections;
	}

	public void setCollections(Set<Collection> collections) {
		this.collections = collections;
	}
	
	
}
