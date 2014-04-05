package com.koleksinaia.rest.controller;

import java.sql.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.koleksinaia.core.entity.Order;
import com.koleksinaia.core.service.OrderService;
import com.koleksinaia.rest.controller.entity.OrderDetail;
import com.koleksinaia.rest.controller.entity.OrderListDetail;
import com.koleksinaia.rest.controller.response.SearchResult;

@Controller
@RequestMapping("/orders")
public class OrderController {

	//private static Logger LOG = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
	private OrderService orderService;
	
	/**
	 * Searches orders based on provided parameters
	 * @param page
	 * @param sort
	 * @param startDateLong
	 * @param endDateLong
	 * @param customerId
	 * @param supplierId
	 * @return search result object
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
    public SearchResult<OrderListDetail> searchOrder(
    		@RequestParam(value="page", defaultValue="1") int page,
    		@RequestParam(value="sort", defaultValue="newest") String sort,
    		@RequestParam(value="startDate", defaultValue="0") long startDateLong,
    		@RequestParam(value="endDate", defaultValue="0") long endDateLong,
    		@RequestParam(value="customerId", defaultValue="") String customerId,
    		@RequestParam(value="supplierId", defaultValue=(String) "") String supplierId) {
		
		//TODO validation on params
		Date startDate = (startDateLong == 0) ? null : new Date(startDateLong);
		Date endDate = (endDateLong == 0) ? null : new Date(endDateLong);
		     
        return OrderListDetail.getOrderSearchResult(orderService.customSearch(page, sort, startDate, endDate, customerId, supplierId));
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
    public ResponseEntity<OrderDetail> viewOrder(@PathVariable int id) {
		Order order = orderService.findByOrderId(id);

        if (order == null) { // not found
            return new ResponseEntity<OrderDetail>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<OrderDetail>(new OrderDetail(order), HttpStatus.OK);
    }	
	
	/**
	 * Handles request for updating order details
	 * @param order
	 * @param id
	 * @param builder
	 * @return Http Ok (200) and updated order if succeed, or Http not found (404) if order id is not exist
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public ResponseEntity<OrderDetail> updateOrder(@RequestBody Order order, @PathVariable int id, UriComponentsBuilder builder) {
		Order exist = orderService.findByOrderId(id);
        if (exist == null) { // not found
            return new ResponseEntity<OrderDetail>(HttpStatus.NOT_FOUND);
        }
		
        Order savedOrder = orderService.saveOrder(order);		
		return new ResponseEntity<OrderDetail>(new OrderDetail(savedOrder), HttpStatus.OK);
    }
	
	@RequestMapping(method = RequestMethod.GET, params = "detail=uncollected")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
    public List<OrderListDetail> getUncollectedOrdersOfSupplier(@RequestParam(value="supplierId", required=true) String supplierId) {

        return OrderListDetail.getOrderList(orderService.findUncollectedOrdersBySupplier(supplierId));
    }
	
	@RequestMapping(method = RequestMethod.GET, params = "detail=unpurchased")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
    public List<OrderListDetail> getUnpurchasedOrdersOfSupplier(@RequestParam(value="supplierId", required=true) String supplierId) {

        return OrderListDetail.getOrderList(orderService.findUnpurchasedOrdersBySupplier(supplierId));
    }
	
	@RequestMapping(method = RequestMethod.GET, params = "detail=unshipped")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
    public List<OrderListDetail> getUnshippedOrdersOfCustomer(@RequestParam(value="customerId", required=true) String customerId) {

        return OrderListDetail.getOrderList(orderService.findUnshippedOrdersOfCustomer(customerId));
    }
	
	@RequestMapping(method = RequestMethod.GET, params = "detail=unpaid")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
    public List<OrderListDetail> getUnpaidOrdersOfCustomer(@RequestParam(value="customerId", required=true) String customerId) {

        return OrderListDetail.getOrderList(orderService.findUnpaidOrdersOfCustomer(customerId));
    }
	
}
