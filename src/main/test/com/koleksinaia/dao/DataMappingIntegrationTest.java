package com.koleksinaia.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import com.koleksinaia.config.JPAConfiguration;

import static com.koleksinaia.dao.fixture.JPAAssertions.assertTableExists;
import static com.koleksinaia.dao.fixture.JPAAssertions.assertTableHasColumn;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JPAConfiguration.class})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class DataMappingIntegrationTest {

	@Autowired
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
}
