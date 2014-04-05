package com.koleksinaia.core.service;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import com.koleksinaia.core.entity.Collection;

public interface CollectionService {

	Collection findByCollectionId(int id);
	
	boolean collectionIdExists(int id);
	
	List<Collection> findAllCollections();
	
	Page<Collection> findCollections(int page, String sortDirection, Date startDate, Date endDate, String supplierId);
	 
	Collection saveCollection(Collection collection);
	    
	boolean deleteCollection(String id);

	Collection updateCollection(Collection collection);

	List<Collection> findUnpurchasedCollectionsBySupplier(String supplierId);
}
