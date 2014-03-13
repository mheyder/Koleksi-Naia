package com.koleksinaia.rest.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;

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

import com.koleksinaia.core.entity.Supplier;
import com.koleksinaia.core.service.SupplierService;

public class SupplierControllerIntegrationTest {

	MockMvc mockMvc;
	
	@InjectMocks
	SupplierController controller;
	
	@Mock
	SupplierService supplierService;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		this.mockMvc = standaloneSetup(controller)
				.setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
	}
	
	/** 
	 * Test get all suppliers
	 */
	@Test
	public void test_01_getAllSuppliers() throws Exception {
		when(supplierService.findAllSuppliers()).thenReturn(allSuppliers());
		
		this.mockMvc.perform(
	            get("/suppliers")
	              .accept(MediaType.APPLICATION_JSON))
	              .andExpect(status().isOk())
	              .andExpect(jsonPath("$[0].name").value(SUPPLIER_1_NAME));
	}
	
	/** 
	 * Succeed create will return HTTP Created (201) and supplier id
	 */
	@Test
	public void test_02_createSupplierSuccesfully() throws Exception {
		when(supplierService.supplierIdExists(anyString())).thenReturn(false);
		when(supplierService.saveSupplier(any(Supplier.class))).thenReturn(standardSupplier());
		
		this.mockMvc.perform(
				post("/suppliers")
					.content(standardSupplierJSON())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(SUPPLIER_1_ID));
	}
	
	
	/** 
	 * Failed create (validation error) will return HTTP Bad Request (400)
	 */
	@Test
	public void test_03_createSupplierFailed() throws Exception {
		when(supplierService.supplierIdExists(anyString())).thenReturn(true);
		
		this.mockMvc.perform(
				post("/suppliers")
					.content(standardSupplierJSON())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
	}
	
	/** 
	 * Succeed view will return HTTP OK (200) and rendered correctly
	 */
	
	@Test
	public void test_04_viewSupplierSuccessfully() throws Exception {
		when(supplierService.findBySupplierId(anyString())).thenReturn(standardSupplier());
		
		this.mockMvc.perform(
				get("/suppliers/{id}",  String.valueOf(SUPPLIER_1_ID))
                	.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(SUPPLIER_1_ID))
                .andExpect(jsonPath("$.name").value(SUPPLIER_1_NAME));
	}
	
	/**
	 * Failed view will return HTTP Not Found (404)
	 */
	@Test
	public void test_05_viewSupplierHttpNotFound() throws Exception {
		when(supplierService.findBySupplierId(anyString())).thenReturn(null);
		
		this.mockMvc.perform(
				get("/suppliers/{id}",  String.valueOf(SUPPLIER_1_ID))
                	.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
	}	

	/** 
	 * Succeed update will return HTTP OK (200) and rendered correctly as JSON
	 */	
	@Test
	public void test_06_updateSupplierSuccesfully() throws Exception {
		when(supplierService.findBySupplierId(anyString())).thenReturn(standardSupplier());
		when(supplierService.saveSupplier(any(Supplier.class))).thenReturn(standardSupplier());
		
		this.mockMvc.perform(
				put("/suppliers/{id}",  String.valueOf(SUPPLIER_1_ID))
					.content(standardSupplierJSON())
					.contentType(MediaType.APPLICATION_JSON)
                	.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(SUPPLIER_1_ID))
                .andExpect(jsonPath("$.name").value(SUPPLIER_1_NAME));
	}
	
	/**
	 * Failed edit (supplier not exist) will return HTTP Not Found (404)
	 */
	@Test
	public void test_07_updateSupplierFailedNotExist() throws Exception {
		when(supplierService.findBySupplierId(anyString())).thenReturn(null);
		
		this.mockMvc.perform(
				put("/suppliers/{id}",  String.valueOf(SUPPLIER_1_ID))
					.content(standardSupplierJSON())
					.contentType(MediaType.APPLICATION_JSON)
                	.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
	}
	
//	/**
//	 * Succeed Delete will return HTTP OK and rendered correctly
//	 * @throws Exception
//	 */	
//	@Test
//	public void test_10_deleteSupplierSuccesfully() throws Exception {
//		when(supplierService.findBySupplierId(anyString())).thenReturn(standardSupplier());
//		when(supplierService.deleteSupplier(anyString())).thenReturn(true);
//		
//		this.mockMvc.perform(
//				delete("/rest/suppliers/{id}",  String.valueOf(SUPPLIER_1_ID))
//                	.accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(SUPPLIER_1_ID))
//                .andExpect(jsonPath("$.name").value(SUPPLIER_1_NAME));
//	}
//	
//	/**
//	 * Failed edit (validation error) will return HTTP Bad Request (400) and rendered correctly as JSON
//	 * @throws Exception
//	 */	
//	@Test
//	public void test_11_deleteSupplierFailedValidation() throws Exception {
//		when(supplierService.findBySupplierId(anyString())).thenReturn(standardSupplier());
//		when(supplierService.deleteSupplier(anyString())).thenReturn(false);
//		
//		this.mockMvc.perform(
//				delete("/rest/suppliers/{id}",  String.valueOf(SUPPLIER_1_ID))
//                	.accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.id").value(SUPPLIER_1_ID))
//                .andExpect(jsonPath("$.name").value(SUPPLIER_1_NAME));
//	}
//	
//	/**
//	 * Failed delete (supplier not exist) will return HTTP Not Found (404)
//	 * @throws Exception
//	 */
//	@Test
//	public void test_12_deleteSupplierFailedNotExist() throws Exception {
//		when(supplierService.findBySupplierId(anyString())).thenReturn(null);
//		
//		this.mockMvc.perform(
//				delete("/rest/suppliers/{id}",  String.valueOf(SUPPLIER_1_ID))
//                	.accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound());
//	}	
}
