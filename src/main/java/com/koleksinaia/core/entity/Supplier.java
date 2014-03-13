package com.koleksinaia.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "supplier")
public class Supplier {

	@Id
	@Column(name = "supplier_id", unique = true)
	private String id;
	
	@Column(name = "supplier_name")
	private String name;
	
	@Column(name = "supplier_cp")
	private String contactPerson;
	
	@Column(name = "supplier_phone")
	private String phone;
	
	@Column(name = "supplier_bbm")
	private String bbm;
	
	@Column(name = "supplier_addr")
	private String address;
	
	@Column(name = "supplier_info")
	private String info;
	
	public Supplier() {
		
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getBbm() {
		return bbm;
	}
	public void setBbm(String bbm) {
		this.bbm = bbm;
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
	
	
}
