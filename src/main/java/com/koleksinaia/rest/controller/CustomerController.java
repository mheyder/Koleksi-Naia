package com.koleksinaia.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.koleksinaia.core.entity.Customer;
import com.koleksinaia.core.service.CustomerService;

@Controller
@RequestMapping("/customers")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	/**
	 * Handles request for getting list of customers
	 * @return list of customers
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Customer> getAllCustomers() {
		return customerService.findAllCustomers(); 
	}
	
	/**
	 * Handles request for creating a new customer
	 * @param customer
	 * @param builder
	 * @return Http Created (201) and customer id if succeed, or Http bad request (400) if customer id is exist
	 */
	@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer, UriComponentsBuilder builder) {
        if (customerService.customerIdExists(customer.getId())) {
        	return new ResponseEntity<Customer>(HttpStatus.BAD_REQUEST);
        }

        Customer savedCustomer = customerService.saveCustomer(customer);
        return new ResponseEntity<Customer>(savedCustomer, HttpStatus.CREATED);
    }

	/**
	 * Handles request for getting a specific customer details
	 * @param id
	 * @return Http Ok (200) and customer details if succeed, or Http not found (404) if customer id is not exist
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<Customer> viewCustomer(@PathVariable String id) {
    	Customer customer = customerService.findByCustomerId(id);

        if (customer == null) { // not found
            return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Customer>(customer, HttpStatus.OK);
    }
	
	/**
	 * Handles request for updating customer details
	 * @param customer
	 * @param id
	 * @param builder
	 * @return Http Ok (200) and updated customer if succeed, or Http not found (404) if customer id is not exist
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer, @PathVariable String id, UriComponentsBuilder builder) {
		Customer exist = customerService.findByCustomerId(id);
        if (exist == null) { // not found
            return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
        }
		
		Customer savedCustomer = customerService.saveCustomer(customer);		
		return new ResponseEntity<Customer>(savedCustomer, HttpStatus.OK);
    }
}
