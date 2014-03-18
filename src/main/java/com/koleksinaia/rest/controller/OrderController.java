package com.koleksinaia.rest.controller;

import java.util.ArrayList;
import java.util.Arrays;
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

import com.koleksinaia.core.entity.Order;
import com.koleksinaia.core.service.OrderService;

@Controller
@RequestMapping("/orders")
public class OrderController {

	//private static Logger LOG = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
	private OrderService orderService;
	
	/**
	 * Handles request for getting list of orders
	 * @return list of suppliers
	 */
	//TODO add pagination and filtering
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Order> getAllOrderss() {
		
//		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
//		try {
//			String json = ow.writeValueAsString(orderService.findAllOrders());
//			LOG.warn("getAllOrderss order json: "+ json);
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		return orderService.findAllOrders(); 
	}
	
	/**
	 * Handles request for creating new orders
	 * @param orders
	 * @param builder
	 * @return Http Created (201) if succeed, or Http bad request (400) if supplier id is exist
	 */
	@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Order> createOrder(@RequestBody Order[] ordersInArray, UriComponentsBuilder builder) {
		List<Order> orders = new ArrayList<Order>(Arrays.asList(ordersInArray));
		return (orderService.saveOrders(orders)) ?  new ResponseEntity<Order>(HttpStatus.CREATED) : new ResponseEntity<Order>(HttpStatus.BAD_REQUEST);
    }
	
	/**
	 * Handles request for getting a specific order details
	 * @param id
	 * @return Http Ok (200) and order details if succeed, or Http not found (404) if order id is not exist
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<Order> viewOrder(@PathVariable int id) {
		Order order = orderService.findByOrderId(id);

        if (order == null) { // not found
            return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }
	
	/**
	 * Handles request for updating order details
	 * @param order
	 * @param id
	 * @param builder
	 * @return Http Ok (200) and updated order if succeed, or Http not found (404) if order id is not exist
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public ResponseEntity<Order> updateOrder(@RequestBody Order order, @PathVariable int id, UriComponentsBuilder builder) {
		Order exist = orderService.findByOrderId(id);
        if (exist == null) { // not found
            return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
        }
		
        Order savedOrder = orderService.saveOrder(order);		
		return new ResponseEntity<Order>(savedOrder, HttpStatus.OK);
    }
}
