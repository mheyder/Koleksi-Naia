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

import com.koleksinaia.core.entity.Collection;
import com.koleksinaia.core.service.CollectionService;
import com.koleksinaia.rest.controller.entity.CollectionDetail;
import com.koleksinaia.rest.controller.entity.CollectionListDetail;
import com.koleksinaia.rest.controller.response.ResponseMessage;
import com.koleksinaia.rest.controller.response.ResponseObject;
import com.koleksinaia.rest.controller.response.SearchResult;

@Controller
@RequestMapping("/collection")
public class CollectionController {

	@Autowired
	private CollectionService collectionService;
	
	/**
	 * Handles request for getting list of purchases
	 * @return list of purchases
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public SearchResult<CollectionListDetail> searchCollection(
    		@RequestParam(value="page", defaultValue="1") int page,
    		@RequestParam(value="sort", defaultValue="newest") String sort,
    		@RequestParam(value="startDate", defaultValue="0") long startDateLong,
    		@RequestParam(value="endDate", defaultValue="0") long endDateLong,
    		@RequestParam(value="supplierId", defaultValue=(String) "") String supplierId) {
		
		//TODO validation on params
		Date startDate = (startDateLong == 0) ? null : new Date(startDateLong);
		Date endDate = (endDateLong == 0) ? null : new Date(endDateLong);
		     
        return CollectionListDetail.getCollectionSearchResult(collectionService.findCollections(page, sort, startDate, endDate, supplierId));
    }
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<ResponseObject<Object>> collectOrders(@RequestBody Collection collection, UriComponentsBuilder builder) {
//		if ((collection == null) || (collection.getOrders() == null) || collection.getOrders().isEmpty()) {
//			return new ResponseEntity<ResponseObject<Object>>(HttpStatus.BAD_REQUEST);
//		}

		if (collectionService.saveCollection(collection) != null) {
			ResponseObject<Object> response = new ResponseObject<Object>();
			response.addMessage(new ResponseMessage(ResponseMessage.MSG_TYPE_SUCCEED, collection.getOrders().size() +" orders successfully collected from "+ collection.getSupplier().getName()+"."));
			return new ResponseEntity<ResponseObject<Object>>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<ResponseObject<Object>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	@ResponseBody
    public ResponseEntity<CollectionDetail> viewCollection(@PathVariable int id) {
		Collection collection = collectionService.findByCollectionId(id);

        if (collection == null) { // not found
            return new ResponseEntity<CollectionDetail>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<CollectionDetail>(new CollectionDetail(collection), HttpStatus.OK);
    }
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public ResponseEntity<CollectionDetail> updateCollection(@RequestBody Collection collection, @PathVariable int id, UriComponentsBuilder builder) {
        if (!collectionService.collectionIdExists(id)) { // not found
            return new ResponseEntity<CollectionDetail>(HttpStatus.NOT_FOUND);
        }
        
        Collection savedCollection = collectionService.saveCollection(collection);
        if (savedCollection != null) {
			return new ResponseEntity<CollectionDetail>(new CollectionDetail(savedCollection), HttpStatus.OK);
		} else {
			return new ResponseEntity<CollectionDetail>(HttpStatus.BAD_REQUEST);
		}
    }
	
	@RequestMapping(method = RequestMethod.GET, params = "detail=unpurchased")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
    public List<CollectionListDetail> getUnpurchasedCollectionOfSupplier(@RequestParam(value="supplierId", required=true) String supplierId) {

        return CollectionListDetail.getCollectionList(collectionService.findUnpurchasedCollectionsBySupplier(supplierId));
    }
}
