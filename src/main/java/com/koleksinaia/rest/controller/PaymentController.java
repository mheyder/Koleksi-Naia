package com.koleksinaia.rest.controller;

import java.sql.Date;

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

import com.koleksinaia.core.entity.Payment;
import com.koleksinaia.core.service.PaymentService;
import com.koleksinaia.rest.controller.entity.PaymentDetail;
import com.koleksinaia.rest.controller.entity.PaymentListDetail;
import com.koleksinaia.rest.controller.response.SearchResult;

@Controller
@RequestMapping("/payments")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public SearchResult<PaymentListDetail> searchPurchases(
    		@RequestParam(value="page", defaultValue="1") int page,
    		@RequestParam(value="sort", defaultValue="newest") String sort,
    		@RequestParam(value="startDate", defaultValue="0") long startDateLong,
    		@RequestParam(value="endDate", defaultValue="0") long endDateLong,
    		@RequestParam(value="customerId", defaultValue=(String) "") String customerId) {
		
		//TODO validation on params
		Date startDate = (startDateLong == 0) ? null : new Date(startDateLong);
		Date endDate = (endDateLong == 0) ? null : new Date(endDateLong);
				
		return PaymentListDetail.getPaymentSearchResult(paymentService.findPayments(page, sort, startDate, endDate, customerId)); 
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<PaymentDetail> viewPayment(@PathVariable int id) {
		Payment payment = paymentService.findByPaymentId(id);

        if (payment == null) { // not found
            return new ResponseEntity<PaymentDetail>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<PaymentDetail>(new PaymentDetail(payment), HttpStatus.OK);
    }
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<PaymentDetail> makePayment(@RequestBody Payment payment, UriComponentsBuilder builder) {
		if (paymentService.savePayment(payment) != null) {
			return new ResponseEntity<PaymentDetail>( HttpStatus.OK);
		} else {
			return new ResponseEntity<PaymentDetail>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public ResponseEntity<PaymentDetail> updatePurchase(@RequestBody Payment payment, @PathVariable int id, UriComponentsBuilder builder) {
        if (!paymentService.paymentIdExists(id)) { // not found
            return new ResponseEntity<PaymentDetail>(HttpStatus.NOT_FOUND);
        }
        
        Payment savedPayment = paymentService.savePayment(payment);
        if (savedPayment != null) {
			return new ResponseEntity<PaymentDetail>(new PaymentDetail(savedPayment), HttpStatus.OK);
		} else {
			return new ResponseEntity<PaymentDetail>(HttpStatus.BAD_REQUEST);
		}
    }
}
