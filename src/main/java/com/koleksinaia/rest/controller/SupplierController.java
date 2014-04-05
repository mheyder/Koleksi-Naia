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

import com.koleksinaia.core.entity.Supplier;
import com.koleksinaia.core.service.SupplierService;
import com.koleksinaia.rest.controller.entity.SupplierListDetail;
import com.koleksinaia.rest.controller.entity.SupplierNameListDetail;
import com.koleksinaia.rest.controller.entity.SupplierStatisticListDetail;

@Controller
@RequestMapping("/suppliers")
public class SupplierController {

	//private static Logger LOG = LoggerFactory.getLogger(SupplierController.class);
	
	@Autowired
	private SupplierService supplierService;
	
	/**
	 * Handles request for getting list of suppliers
	 * @return list of suppliers
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<SupplierListDetail> getAllSuppliers() {
		//TODO pagination		
		return SupplierListDetail.getSupplierList(supplierService.findAllSuppliers());
	}
	
	@RequestMapping(method = RequestMethod.GET, params = "detail=namelist")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<SupplierNameListDetail> getSupplierNameList() {
		return SupplierNameListDetail.getSupplierList(supplierService.findAllSuppliers());
	}
	
	/**
	 * Handles request for creating a new supplier
	 * @param supplier
	 * @param builder
	 * @return Http Created (201) and supplier id if succeed, or Http bad request (400) if supplier id is exist
	 */
	@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Supplier> createSupplier(@RequestBody Supplier supplier, UriComponentsBuilder builder) {
        if (supplierService.supplierIdExists(supplier.getId())) {
        	return new ResponseEntity<Supplier>(HttpStatus.BAD_REQUEST);
        }

        Supplier savedSupplier = supplierService.saveSupplier(supplier);
        return new ResponseEntity<Supplier>(savedSupplier, HttpStatus.CREATED);
    }

	/**
	 * Handles request for getting a specific supplier details
	 * @param id
	 * @return Http Ok (200) and supplier details if succeed, or Http not found (404) if supplier id is not exist
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<Supplier> viewSupplier(@PathVariable String id) {
    	Supplier supplier = supplierService.findBySupplierId(id);

        if (supplier == null) { // not found
            return new ResponseEntity<Supplier>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Supplier>(supplier, HttpStatus.OK);
    }
	
	/**
	 * Handles request for updating supplier details
	 * @param supplier
	 * @param id
	 * @param builder
	 * @return Http Ok (200) and updated supplier if succeed, or Http not found (404) if supplier id is not exist
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public ResponseEntity<Supplier> updateSupplier(@RequestBody Supplier supplier, @PathVariable String id, UriComponentsBuilder builder) {
		Supplier exist = supplierService.findBySupplierId(id);
        if (exist == null) { // not found
            return new ResponseEntity<Supplier>(HttpStatus.NOT_FOUND);
        }
		
		Supplier savedSupplier = supplierService.saveSupplier(supplier);		
		return new ResponseEntity<Supplier>(savedSupplier, HttpStatus.OK);
    }
	
	@RequestMapping(method = RequestMethod.GET, params = "detail=uncollected")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<SupplierStatisticListDetail> getSupplierUncollectedOrderList() {
		return SupplierStatisticListDetail.getSupplierList(supplierService.findSuppliersWithUncollectedOrders());
	}
	
	@RequestMapping(method = RequestMethod.GET, params = "detail=unpurchased")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<SupplierStatisticListDetail> getSupplierUnpurchasedOrderList() {
		return SupplierStatisticListDetail.getSupplierList(supplierService.findSuppliersWithUnpurchasedOrders());
	}
	
//	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
//    public ResponseEntity<Supplier> deleteSupplier(@PathVariable String id) {        
//		Supplier supplier = supplierService.findBySupplierId(id);		
//        if (supplier == null) { // not found
//            return new ResponseEntity<Supplier>(HttpStatus.NOT_FOUND);
//        }
//
//        if (supplierService.deleteSupplier(id)) {
//            return new ResponseEntity<Supplier>(supplier, HttpStatus.OK);
//        }
//        return new ResponseEntity<Supplier>(supplier, HttpStatus.BAD_REQUEST);
//        
//    }
}
