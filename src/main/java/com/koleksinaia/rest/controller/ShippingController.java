package com.koleksinaia.rest.controller;

import java.sql.Date;
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

import com.koleksinaia.core.entity.Shipping;
import com.koleksinaia.core.service.ShippingService;
import com.koleksinaia.rest.controller.entity.CollectionListDetail;
import com.koleksinaia.rest.controller.entity.ShippingDetail;
import com.koleksinaia.rest.controller.entity.ShippingListDetail;
import com.koleksinaia.rest.controller.response.ResponseMessage;
import com.koleksinaia.rest.controller.response.ResponseObject;
import com.koleksinaia.rest.controller.response.SearchResult;

@Controller
@RequestMapping("/shipping")
public class ShippingController {

	@Autowired
	private ShippingService shippingService;
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public SearchResult<ShippingListDetail> searchShipping(
    		@RequestParam(value="page", defaultValue="1") int page,
    		@RequestParam(value="sort", defaultValue="newest") String sort,
    		@RequestParam(value="startDate", defaultValue="0") long startDateLong,
    		@RequestParam(value="endDate", defaultValue="0") long endDateLong,
    		@RequestParam(value="customerId", defaultValue=(String) "") String customerId) {
		
		//TODO validation on params
		Date startDate = (startDateLong == 0) ? null : new Date(startDateLong);
		Date endDate = (endDateLong == 0) ? null : new Date(endDateLong);
		     
        return ShippingListDetail.getShippingSearchResult(shippingService.findShippings(page, sort, startDate, endDate, customerId));
    }
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<ResponseObject<Object>> shipOrders(@RequestBody Shipping shipping, UriComponentsBuilder builder) {
		if (shippingService.saveShipping(shipping) != null) {
			ResponseObject<Object> response = new ResponseObject<Object>();
			response.addMessage(new ResponseMessage(ResponseMessage.MSG_TYPE_SUCCEED, shipping.getOrders().size() +" orders successfully shipped to "+ shipping.getCustomer().getName()+"."));
			return new ResponseEntity<ResponseObject<Object>>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<ResponseObject<Object>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	@ResponseBody
    public ResponseEntity<ShippingDetail> viewShipping(@PathVariable int id) {
		Shipping shipping = shippingService.findByShippingId(id);

        if (shipping == null) { // not found
            return new ResponseEntity<ShippingDetail>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<ShippingDetail>(new ShippingDetail(shipping), HttpStatus.OK);
    }
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public ResponseEntity<ShippingDetail> updateShippingn(@RequestBody Shipping shipping, @PathVariable int id, UriComponentsBuilder builder) {
        if (!shippingService.shippingIdExists(id)) { // not found
            return new ResponseEntity<ShippingDetail>(HttpStatus.NOT_FOUND);
        }
        
        Shipping savedShipping = shippingService.saveShipping(shipping);
        if (savedShipping != null) {
			return new ResponseEntity<ShippingDetail>(new ShippingDetail(savedShipping), HttpStatus.OK);
		} else {
			return new ResponseEntity<ShippingDetail>(HttpStatus.BAD_REQUEST);
		}
    }
	
	@RequestMapping(method = RequestMethod.GET, params = "detail=unpaid")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
    public List<ShippingListDetail> getUnpaidShippingOfCustomer(@RequestParam(value="customerId", required=true) String customerId) {

        return ShippingListDetail.getShippingList(shippingService.findUnpaidShippingsByCustomer(customerId));
    }
}
