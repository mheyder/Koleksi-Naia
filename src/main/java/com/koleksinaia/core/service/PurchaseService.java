package com.koleksinaia.core.service;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import com.koleksinaia.core.entity.Purchase;

public interface PurchaseService {

	Purchase findByPurchaseId(int id);
	
	List<Purchase> findAllPurchases();
	 
	Purchase savePurchase(Purchase purchase);
	    
	boolean deletePurchase(int id);

	Page<Purchase> findPurchases(int page, String sortDirection, Date startDate, Date endDate, String supplierId);

	boolean purchaseIdExists(int id);
}
