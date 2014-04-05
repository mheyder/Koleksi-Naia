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

import com.koleksinaia.core.entity.Purchase;
import com.koleksinaia.core.service.PurchaseService;
import com.koleksinaia.rest.controller.entity.PurchaseDetail;
import com.koleksinaia.rest.controller.entity.PurchaseListDetail;
import com.koleksinaia.rest.controller.response.SearchResult;

@Controller
@RequestMapping("/purchases")
public class PurchaseController {

	@Autowired
	private PurchaseService purchaseService;
	
	/**
	 * Handles request for getting list of purchases
	 * @return list of purchases
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public SearchResult<PurchaseListDetail> searchPurchases(
    		@RequestParam(value="page", defaultValue="1") int page,
    		@RequestParam(value="sort", defaultValue="newest") String sort,
    		@RequestParam(value="startDate", defaultValue="0") long startDateLong,
    		@RequestParam(value="endDate", defaultValue="0") long endDateLong,
    		@RequestParam(value="supplierId", defaultValue=(String) "") String supplierId) {
		
		//TODO validation on params
		Date startDate = (startDateLong == 0) ? null : new Date(startDateLong);
		Date endDate = (endDateLong == 0) ? null : new Date(endDateLong);
				
		return PurchaseListDetail.getPurchaseSearchResult(purchaseService.findPurchases(page, sort, startDate, endDate, supplierId)); 
	}
	
	/**
	 * Handles request for getting a specific purchase details
	 * @param id
	 * @return Http Ok (200) and customer details if succeed, or Http not found (404) if customer id is not exist
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<PurchaseDetail> viewPurchase(@PathVariable int id) {
		Purchase purchase = purchaseService.findByPurchaseId(id);

        if (purchase == null) { // not found
            return new ResponseEntity<PurchaseDetail>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<PurchaseDetail>(new PurchaseDetail(purchase), HttpStatus.OK);
    }
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<PurchaseDetail> purchaseOrders(@RequestBody Purchase purchase, UriComponentsBuilder builder) {
		if (purchaseService.savePurchase(purchase) != null) {
//			ResponseObject<PurchaseDetail> response = new ResponseObject<Object>();
//			response.addMessage(new ResponseMessage(ResponseMessage.MSG_TYPE_SUCCEED, purchase.getOrders().size() +" orders successfully collected from "+ purchase.getSupplier().getName()+"."));
			return new ResponseEntity<PurchaseDetail>( HttpStatus.OK);
		} else {
			return new ResponseEntity<PurchaseDetail>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public ResponseEntity<PurchaseDetail> updatePurchase(@RequestBody Purchase purchase, @PathVariable int id, UriComponentsBuilder builder) {
        if (!purchaseService.purchaseIdExists(id)) { // not found
            return new ResponseEntity<PurchaseDetail>(HttpStatus.NOT_FOUND);
        }
        
        Purchase savedPurchase = purchaseService.savePurchase(purchase);
        if (savedPurchase != null) {
			return new ResponseEntity<PurchaseDetail>(new PurchaseDetail(savedPurchase), HttpStatus.OK);
		} else {
			return new ResponseEntity<PurchaseDetail>(HttpStatus.BAD_REQUEST);
		}
    }
}
