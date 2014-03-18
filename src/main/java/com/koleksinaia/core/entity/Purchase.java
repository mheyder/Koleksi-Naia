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
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name = "purchase_id", unique = true)
	private int id;
	
	@Column(name = "purchase_date", nullable = false)
	private Date date;
	
	@Column(name = "purchase_total")
	private long total;
	
	@Column(name = "purchase_info")
	private String info;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "supplier_id", nullable = false)
	private Supplier supplier;
	
	@OneToMany(mappedBy="purchase", fetch = FetchType.LAZY)
	private Set<Order> Orders;
	
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

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
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
		return Orders;
	}

	public void setOrders(Set<Order> orders) {
		Orders = orders;
	}
	
	
}
