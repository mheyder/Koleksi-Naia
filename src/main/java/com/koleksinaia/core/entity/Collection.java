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

@Entity(name = "collection")
public class Collection {

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name = "collection_id", unique = true)
	private int id;
	
	@Column(name = "collection_date", nullable = false)
	private Date date;
	
	@Column(name = "collection_info")
	private String info;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "supplier_id", nullable = false)
	private Supplier supplier;
	
	@OneToMany(mappedBy="collection", fetch = FetchType.LAZY)
	private Set<Order> Orders;
	
	public Collection() {
		
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

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Set<Order> getOrders() {
		return Orders;
	}

	public void setOrders(Set<Order> orders) {
		Orders = orders;
	}
	
}
