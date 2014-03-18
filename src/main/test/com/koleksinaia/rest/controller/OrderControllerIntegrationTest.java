package com.koleksinaia.rest.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static com.koleksinaia.rest.controller.fixture.RestDataFixture.*;

import com.koleksinaia.core.entity.Order;
import com.koleksinaia.core.service.OrderService;

public class OrderControllerIntegrationTest {
	
	private static Logger LOG = LoggerFactory.getLogger(SupplierController.class);

	MockMvc mockMvc;
	
	@InjectMocks
	OrderController controller;
	
	@Mock
	OrderService orderService;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		this.mockMvc = standaloneSetup(controller)
				.setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
	}
	
	/** 
	 * Test get all orders
	 */
	@Test
	public void test_01_getAllOrderss() throws Exception {
		when(orderService.findAllOrders()).thenReturn(allOrders());
		
		this.mockMvc.perform(
	            get("/orders")
	              .accept(MediaType.APPLICATION_JSON))
	              .andExpect(status().isOk())
	              .andExpect(jsonPath("$[0].id").value(ORDER_1_ID));
	}
	
	/** 
	 * Succeed create will return HTTP Created (201)
	 */
	@Test
	public void test_02_createOrderSuccesfully() throws Exception {
		when(orderService.saveOrders(anyListOf(Order.class))).thenReturn(true);
		
		LOG.warn("meir json : " + standardOrdersJSON());
		
		this.mockMvc.perform(
				post("/orders")
					.content(standardOrdersJSON())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
	}
	
	
	/** 
	 * Failed create (validation error) will return HTTP Bad Request (400)
	 */
	@Test
	public void test_03_createOrderFailed() throws Exception {
		when(orderService.saveOrders(anyListOf(Order.class))).thenReturn(false);
		
		this.mockMvc.perform(
				post("/orders")
					.content(standardOrdersJSON())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
	}
	
	/** 
	 * Succeed view will return HTTP OK (200) and rendered correctly
	 */
	@Test
	public void test_04_viewOrderSuccessfully() throws Exception {
		when(orderService.findByOrderId(anyInt())).thenReturn(standardOrder());
		
		this.mockMvc.perform(
				get("/orders/{id}",  String.valueOf(ORDER_1_ID))
                	.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ORDER_1_ID))
                .andExpect(jsonPath("$.itemName").value(ORDER_1_ITEM));
	}
	
	/**
	 * Failed view will return HTTP Not Found (404)
	 */
	@Test
	public void test_05_viewOrderHttpNotFound() throws Exception {
		when(orderService.findByOrderId(anyInt())).thenReturn(null);
		
		this.mockMvc.perform(
				get("/orders/{id}",  String.valueOf(ORDER_1_ID))
                	.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
	}
	
	/** 
	 * Succeed update will return HTTP OK (200) and rendered correctly as JSON
	 */	
	@Test
	public void test_06_updateOrderSuccesfully() throws Exception {
		when(orderService.findByOrderId(anyInt())).thenReturn(standardOrder());
		when(orderService.saveOrder(any(Order.class))).thenReturn(standardOrder());
		
		this.mockMvc.perform(
				put("/orders/{id}",  String.valueOf(ORDER_1_ID))
					.content(standardOrderJSON())
					.contentType(MediaType.APPLICATION_JSON)
                	.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ORDER_1_ID))
                .andExpect(jsonPath("$.itemName").value(ORDER_1_ITEM));
	}
	
	/**
	 * Failed edit (order not exist) will return HTTP Not Found (404)
	 */
	@Test
	public void test_07_updateOrderFailedNotExist() throws Exception {
		when(orderService.findByOrderId(anyInt())).thenReturn(null);
		
		this.mockMvc.perform(
				put("/orders/{id}",  String.valueOf(ORDER_1_ID))
					.content(standardOrderJSON())
					.contentType(MediaType.APPLICATION_JSON)
                	.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
	}
}
