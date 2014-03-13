package com.koleksinaia.rest.controller.fixture;

import java.util.ArrayList;
import java.util.List;

import com.koleksinaia.core.entity.Customer;
import com.koleksinaia.core.entity.Supplier;



public class RestDataFixture {

	/*** SUPPLIER DATA FIXTURE ***/	
	public static final String SUPPLIER_1_ID = "SR";
	public static final String SUPPLIER_2_ID = "PINK";
	public static final String SUPPLIER_1_NAME = "Strawberry";
	public static final String SUPPLIER_1_CP = "Ririn";
	public static final String SUPPLIER_1_PHONE = "0812345678";
	public static final String SUPPLIER_1_BBM = "AA34DF";
	public static final String SUPPLIER_1_ADDRESS = "ITC Cempaka Mas";
	public static final String SUPPLIER_1_INFO = "no comment";
	
	public static Supplier standardSupplier() {
		return customSupplier(SUPPLIER_1_ID, SUPPLIER_1_NAME, SUPPLIER_1_CP, SUPPLIER_1_PHONE, SUPPLIER_1_BBM, SUPPLIER_1_ADDRESS, SUPPLIER_1_INFO);
	}
	
	public static Supplier customSupplier(String id, String name, String cp, String phone, String bbm, String address, String info) {
		Supplier supplier = new Supplier();
		supplier.setId(id);
		supplier.setName(name);
		supplier.setContactPerson(cp);
		supplier.setPhone(phone);
		supplier.setBbm(bbm);
		supplier.setAddress(address);
		supplier.setInfo(info);
		return supplier;
	}
	
	public static List<Supplier> allSuppliers() {
		List<Supplier> suppliers = new ArrayList<Supplier>();
	    for(int i=0; i<3; i++) {
	    	suppliers.add(customSupplier(SUPPLIER_1_ID + i, SUPPLIER_1_NAME, SUPPLIER_1_CP, SUPPLIER_1_PHONE, SUPPLIER_1_BBM, SUPPLIER_1_ADDRESS, SUPPLIER_1_INFO));
	    }
	    return suppliers;
	}
	
	public static String standardSupplierJSON() {
		return customSupplierJSON(SUPPLIER_1_ID, SUPPLIER_1_NAME, SUPPLIER_1_CP, SUPPLIER_1_PHONE, SUPPLIER_1_BBM, SUPPLIER_1_ADDRESS, SUPPLIER_1_INFO);
	}
	
	public static String customSupplierJSON(String id, String name, String cp, String phone, String bbm, String address, String info) {
		return "{ \"id\": \""+id+"\", "
				+ "\"name\": \""+name+"\", "
				+ "\"contactPerson\": \""+cp+"\", "
				+ "\"phone\": \""+phone+"\", "
				+ "\"bbm\": \""+bbm+"\", "
				+ "\"address\": \""+address+"\", "
				+ "\"info\": \""+info+"\" }";
	}
	
	/*** SUPPLIER DATA FIXTURE ***/	
	public static final String CUSTOMER_1_ID = "ATI";
	public static final String CUSTOMER_2_ID = "POPPY";
	public static final String CUSTOMER_1_NAME = "Mbak Ati";
	public static final String CUSTOMER_1_PHONE = "0812345678";
	public static final String CUSTOMER_1_BBM = "AA34DF";
	public static final int CUSTOMER_1_DISCOUNT = 3;
	public static final String CUSTOMER_1_ADDRESS = "ITC Cempaka Mas";
	public static final String CUSTOMER_1_INFO = "no comment";
	
	public static Customer standardCustomer() {
		return customCustomer(CUSTOMER_1_ID, CUSTOMER_1_NAME, CUSTOMER_1_PHONE, CUSTOMER_1_BBM, CUSTOMER_1_DISCOUNT, CUSTOMER_1_ADDRESS, CUSTOMER_1_INFO);
	}
	
	public static Customer customCustomer(String id, String name, String phone, String bbm, int discount,String address, String info) {
		Customer customer = new Customer();
		customer.setId(id);
		customer.setName(name);
		customer.setPhone(phone);
		customer.setBbm(bbm);
		customer.setDiscount(discount);
		customer.setAddress(address);
		customer.setInfo(info);
		return customer;
	}
	
	public static List<Customer> allCustomers() {
		List<Customer> customers = new ArrayList<Customer>();
	    for(int i=0; i<3; i++) {
	    	customers.add(customCustomer(CUSTOMER_1_ID+i, CUSTOMER_1_NAME, CUSTOMER_1_PHONE, CUSTOMER_1_BBM, CUSTOMER_1_DISCOUNT, CUSTOMER_1_ADDRESS, CUSTOMER_1_INFO));
	    }
	    return customers;
	}
	
	public static String standardCustomerJSON() {
		return customCustomerJSON(CUSTOMER_1_ID, CUSTOMER_1_NAME, CUSTOMER_1_PHONE, CUSTOMER_1_BBM, CUSTOMER_1_DISCOUNT, CUSTOMER_1_ADDRESS, CUSTOMER_1_INFO);
	}
	
	public static String customCustomerJSON(String id, String name, String phone, String bbm, int discount,String address, String info) {
		return "{ \"id\": \""+id+"\", "
				+ "\"name\": \""+name+"\", "
				+ "\"phone\": \""+phone+"\", "
				+ "\"bbm\": \""+bbm+"\", "
				+ "\"discount\": \""+discount+"\", "
				+ "\"address\": \""+address+"\", "
				+ "\"info\": \""+info+"\" }";
	}
}
