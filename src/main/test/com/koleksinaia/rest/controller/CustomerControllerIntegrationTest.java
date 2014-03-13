package com.koleksinaia.rest.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static com.koleksinaia.rest.controller.fixture.RestDataFixture.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;

import com.koleksinaia.core.entity.Customer;
import com.koleksinaia.core.service.CustomerService;


public class CustomerControllerIntegrationTest {

	MockMvc mockMvc;
	
	@InjectMocks
	CustomerController controller;
	
	@Mock
	CustomerService customerService;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		this.mockMvc = standaloneSetup(controller)
				.setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
	}
	
	/** 
	 * Test get all customers
	 */
	@Test
	public void test_01_getAllCustomers() throws Exception {
		when(customerService.findAllCustomers()).thenReturn(allCustomers());
		
		this.mockMvc.perform(
	            get("/customers")
	              .accept(MediaType.APPLICATION_JSON))
	              .andExpect(status().isOk())
	              .andExpect(jsonPath("$[0].name").value(CUSTOMER_1_NAME));
	}
	
	/** 
	 * Succeed create will return HTTP Created (201) and customer id
	 */
	@Test
	public void test_02_createCustomerSuccesfully() throws Exception {
		when(customerService.customerIdExists(anyString())).thenReturn(false);
		when(customerService.saveCustomer(any(Customer.class))).thenReturn(standardCustomer());
		
		this.mockMvc.perform(
				post("/customers")
					.content(standardCustomerJSON())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(CUSTOMER_1_ID));
	}
	
	
	/** 
	 * Failed create (validation error) will return HTTP Bad Request (400)
	 */
	@Test
	public void test_03_createCustomerFailed() throws Exception {
		when(customerService.customerIdExists(anyString())).thenReturn(true);
		
		this.mockMvc.perform(
				post("/customers")
					.content(standardCustomerJSON())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
	}
	
	/** 
	 * Succeed view will return HTTP OK (200) and rendered correctly
	 */
	
	@Test
	public void test_04_viewCustomerSuccessfully() throws Exception {
		when(customerService.findByCustomerId(anyString())).thenReturn(standardCustomer());
		
		this.mockMvc.perform(
				get("/customers/{id}",  String.valueOf(CUSTOMER_1_ID))
                	.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(CUSTOMER_1_ID))
                .andExpect(jsonPath("$.name").value(CUSTOMER_1_NAME));
	}
	
	/**
	 * Failed view will return HTTP Not Found (404)
	 */
	@Test
	public void test_05_viewCustomerHttpNotFound() throws Exception {
		when(customerService.findByCustomerId(anyString())).thenReturn(null);
		
		this.mockMvc.perform(
				get("/customers/{id}",  String.valueOf(CUSTOMER_1_ID))
                	.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
	}	

	/** 
	 * Succeed update will return HTTP OK (200) and rendered correctly as JSON
	 */	
	@Test
	public void test_06_updateCustomerSuccesfully() throws Exception {
		when(customerService.findByCustomerId(anyString())).thenReturn(standardCustomer());
		when(customerService.saveCustomer(any(Customer.class))).thenReturn(standardCustomer());
		
		this.mockMvc.perform(
				put("/customers/{id}",  String.valueOf(CUSTOMER_1_ID))
					.content(standardCustomerJSON())
					.contentType(MediaType.APPLICATION_JSON)
                	.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(CUSTOMER_1_ID))
                .andExpect(jsonPath("$.name").value(CUSTOMER_1_NAME));
	}
	
	/**
	 * Failed edit (customer not exist) will return HTTP Not Found (404)
	 */
	@Test
	public void test_07_updateCustomerFailedNotExist() throws Exception {
		when(customerService.findByCustomerId(anyString())).thenReturn(null);
		
		this.mockMvc.perform(
				put("/customers/{id}",  String.valueOf(CUSTOMER_1_ID))
					.content(standardCustomerJSON())
					.contentType(MediaType.APPLICATION_JSON)
                	.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
	}
}
