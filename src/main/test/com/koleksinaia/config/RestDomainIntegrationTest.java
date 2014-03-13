package com.koleksinaia.config;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static com.koleksinaia.rest.controller.fixture.RestDataFixture.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {MVCConfig.class, JPAConfiguration.class})
@EnableWebMvc
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class RestDomainIntegrationTest {

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	}
	
	@Test
	public void testSupplierDomain() throws Exception  {
		// add new supplier
		this.mockMvc.perform(
				post("/suppliers")
					.content(standardSupplierJSON())
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
	            .andExpect(status().isCreated());
		//get the supplier
	    this.mockMvc.perform(
	            get("/suppliers/" + SUPPLIER_1_ID)
	                    .accept(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("id").value(SUPPLIER_1_ID));
	    // get suppliers list
	    this.mockMvc.perform(
	            get("/suppliers")
	                    .accept(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$[0].id").value(SUPPLIER_1_ID));

	}
	
	@Test
	public void testCustomerDomain() throws Exception  {
		// add new customer
		this.mockMvc.perform(
				post("/customers")
					.content(standardCustomerJSON())
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
	            .andExpect(status().isCreated());
		//get the customer
	    this.mockMvc.perform(
	            get("/customers/" + CUSTOMER_1_ID)
	                    .accept(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("id").value(CUSTOMER_1_ID));
	    // get customers list
	    this.mockMvc.perform(
	            get("/customers")
	                    .accept(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$[0].id").value(CUSTOMER_1_ID));

	  }
}
