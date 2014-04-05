package com.koleksinaia.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.koleksinaia.config.JPAConfiguration;

import static com.koleksinaia.dao.fixture.JPAAssertions.assertTableExists;
import static com.koleksinaia.dao.fixture.JPAAssertions.assertTableHasColumn;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JPAConfiguration.class})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class DataMappingIntegrationTest {

	//@Autowired
	@PersistenceContext
	EntityManager manager;
	
	@Test
	public void testSupplierMappingWorks() throws Exception {
		assertTableExists(manager, "supplier");

	    assertTableHasColumn(manager, "supplier", "supplier_id");
	    assertTableHasColumn(manager, "supplier", "supplier_name");
	    assertTableHasColumn(manager, "supplier", "supplier_cp");
	    assertTableHasColumn(manager, "supplier", "supplier_phone");
	    assertTableHasColumn(manager, "supplier", "supplier_bbm");
	    assertTableHasColumn(manager, "supplier", "supplier_addr");
	    assertTableHasColumn(manager, "supplier", "supplier_info");
	}
	
	@Test
	public void testCustomerMappingWorks() throws Exception {
		assertTableExists(manager, "customer");

	    assertTableHasColumn(manager, "customer", "supplier_id");
	    assertTableHasColumn(manager, "customer", "supplier_name");
	    assertTableHasColumn(manager, "customer", "supplier_phone");
	    assertTableHasColumn(manager, "customer", "supplier_bbm");
	    assertTableHasColumn(manager, "customer", "supplier_disc");
	    assertTableHasColumn(manager, "customer", "supplier_addr");
	    assertTableHasColumn(manager, "customer", "supplier_info");
	}
	
	@Test
	public void testOrderrMappingWorks() throws Exception {
		assertTableExists(manager, "myorder");

	    assertTableHasColumn(manager, "myorder", "order_id");
	    assertTableHasColumn(manager, "myorder", "order_date");
	    assertTableHasColumn(manager, "myorder", "order_dropship");
	    assertTableHasColumn(manager, "myorder", "order_item_name");
	    assertTableHasColumn(manager, "myorder", "order_item_type");
	    assertTableHasColumn(manager, "myorder", "order_cost");
	    assertTableHasColumn(manager, "myorder", "order_price");
	    
	    assertTableHasColumn(manager, "myorder", "customer_id");
	    assertTableHasColumn(manager, "myorder", "supplier_id");
	    assertTableHasColumn(manager, "myorder", "purchase_id");
	    assertTableHasColumn(manager, "myorder", "collection_id");
	    assertTableHasColumn(manager, "myorder", "payment_id");
	    assertTableHasColumn(manager, "myorder", "shipping_id");
	}
	
	@Test
	public void testPurchaseMappingWorks() throws Exception {
		assertTableExists(manager, "purchase");

	    assertTableHasColumn(manager, "customer", "purchase_id");
	    assertTableHasColumn(manager, "customer", "purchase_date");
	    assertTableHasColumn(manager, "customer", "purchase_total");
	    assertTableHasColumn(manager, "customer", "purchase_info");
	    
	    assertTableHasColumn(manager, "customer", "supplier_id");
	}
	
	@Test
	public void testCollectionMappingWorks() throws Exception {
		assertTableExists(manager, "collection");

	    assertTableHasColumn(manager, "customer", "collection_id");
	    assertTableHasColumn(manager, "customer", "collection_date");
	    assertTableHasColumn(manager, "customer", "collection_info");
	    
	    assertTableHasColumn(manager, "customer", "supplier_id");
	}
	
	@Test
	public void testPaymentMappingWorks() throws Exception {
		assertTableExists(manager, "payment");

	    assertTableHasColumn(manager, "customer", "payment_id");
	    assertTableHasColumn(manager, "customer", "payment_date");
	    assertTableHasColumn(manager, "customer", "payment_total");
	    assertTableHasColumn(manager, "customer", "payment_info");
	    
	    assertTableHasColumn(manager, "customer", "customer_id");
	}
	
	@Test
	public void testShippingMappingWorks() throws Exception {
		assertTableExists(manager, "shipping");

	    assertTableHasColumn(manager, "customer", "shipping_id");
	    assertTableHasColumn(manager, "customer", "shipping_date");
	    assertTableHasColumn(manager, "customer", "shipping_info");
	    
	    assertTableHasColumn(manager, "customer", "customer_id");
	}
}
