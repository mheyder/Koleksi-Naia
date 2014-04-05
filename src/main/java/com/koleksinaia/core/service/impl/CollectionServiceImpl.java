package com.koleksinaia.core.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.koleksinaia.core.entity.Collection;
import com.koleksinaia.core.entity.Order;
import com.koleksinaia.core.entity.Supplier;
import com.koleksinaia.core.service.CollectionService;
import com.koleksinaia.dao.CollectionDao;
import com.koleksinaia.dao.OrderDao;
import com.koleksinaia.dao.PurchaseDao;
import com.koleksinaia.dao.SupplierDao;

@Service("collectionService")
@Transactional(readOnly = true)
public class CollectionServiceImpl implements CollectionService {
	private static final int PAGE_SIZE = 20;
	private static final String ASC_SORT_DIRECTION = "oldest";
	private static final String DEFAULT_SORT_PROPERTY = "date";
	private static final long ONE_YEAR_IN_MILLISECONDS = 31556926000L;
	
	@Resource
	private CollectionDao collectionDao;
	
	@Resource
	private OrderDao orderDao;
	
	@Resource
	private SupplierDao supplierDao;
	
	@Resource
	private PurchaseDao purchaseDao;

	@Override
	public Collection findByCollectionId(int id) {
		Collection collection = collectionDao.findOne(id);
		collection.getOrders().size();
		return collection;
	}

	@Override
	public List<Collection> findAllCollections() {
		//return collectionDao.findAllAndFetchOrders();
		
		long s = 1395289181000L;
		long e = 1395919194000L;
		
		Date startDate = new Date(s);
		Date endDate = new Date(e);
		
		PageRequest pageable = new PageRequest(0 ,10, new Sort(new Sort.Order(Sort.Direction.DESC, "date")));
		
		return collectionDao.findByDateBetween(startDate, endDate, pageable).getContent();
	}
	
	@Override
	public Page<Collection> findCollections(int page, String sortDirection,
			Date startDate, Date endDate, String supplierId) {
		Date endDateRange = (endDate != null) ? endDate : new Date(java.util.Calendar.getInstance().getTime().getTime());
		Date startDateRange = (startDate != null) ? startDate : new Date(endDateRange.getTime() - ONE_YEAR_IN_MILLISECONDS);		
		Direction direction = (sortDirection.equalsIgnoreCase(ASC_SORT_DIRECTION)) ? Sort.Direction.ASC : Sort.Direction.DESC;
		Supplier supplier = (supplierId.isEmpty()) ? null : supplierDao.findOne(supplierId);
		
		PageRequest pageable = new PageRequest(page - 1 ,PAGE_SIZE,new Sort(new Sort.Order(direction, DEFAULT_SORT_PROPERTY)));
		if (supplier != null) {
			return collectionDao.findBySupplierAndDateBetween(supplier, startDateRange, endDateRange, pageable);
		}
		return collectionDao.findByDateBetween(startDateRange, endDateRange, pageable);
	}

	@Override
	@Transactional(readOnly = false)
	public Collection saveCollection(Collection newCollection) {
		boolean isNewRecord = !collectionDao.exists(newCollection.getId());		
		Collection collection = (isNewRecord) ? new Collection() : collectionDao.findOne(newCollection.getId());		
		if (isNewRecord) {
			// validate if supplier is exist
			if (supplierDao.exists(newCollection.getSupplier().getId())) {
				collection.setSupplier(supplierDao.findOne(newCollection.getSupplier().getId()));
			} else {
				return null;
			}
		} else {
			for (Order order : collection.getOrders()) {
				order.setCollection(null);
			}
			collection.getOrders().clear();
		}
		
		collection.setDate(newCollection.getDate());
		collection.setCost(newCollection.getCost());
		collection.setInfo(newCollection.getInfo());		

		List<Order> orderIdList = new ArrayList<Order>(newCollection.getOrders());
		List<Order> newOrders = new ArrayList<Order>();
		for(int i=0; i<orderIdList.size(); i++) {
			// validate if order is exist
			if (!orderDao.exists(orderIdList.get(i).getId())) return null;
			Order order = orderDao.findOne(orderIdList.get(i).getId());
			// validate if order is belong to the supplier
			if (!order.getSupplier().getId().equalsIgnoreCase(newCollection.getSupplier().getId())) return null;
			order.setCollection(collection);
			newOrders.add(order);
		}
		collection.setOrders(new HashSet<Order>(newOrders));
		collection.setTotalOrder(newCollection.getOrders().size());

		return collectionDao.save(collection);
	}
	
	@Override
	@Transactional(readOnly = false)
	public Collection updateCollection(Collection newCollection) {
		Collection collection = collectionDao.findOne(newCollection.getId());

		collection.setDate(newCollection.getDate());
		collection.setCost(newCollection.getCost());
		collection.setInfo(newCollection.getInfo());
		
		for (Order order : collection.getOrders()) {
			order.setCollection(null);
		}
		collection.getOrders().clear();
		
		List<Order> orderIdList = new ArrayList<Order>(newCollection.getOrders());
		List<Order> newOrders = new ArrayList<Order>();
		for(int i=0; i<orderIdList.size(); i++) {
			// validate if order is exist
			if (!orderDao.exists(orderIdList.get(i).getId())) return null;
			Order order = orderDao.findOne(orderIdList.get(i).getId());
			// validate if order is belong to the supplier
			if (!order.getSupplier().getId().equalsIgnoreCase(newCollection.getSupplier().getId())) return null;
			order.setCollection(collection);
			newOrders.add(order);
		}
		collection.setOrders(new HashSet<Order>(newOrders));
		collection.setTotalOrder(collection.getOrders().size());

		return collectionDao.save(collection);
	}

	@Override
	public boolean deleteCollection(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean collectionIdExists(int id) {
		return collectionDao.exists(id);
	}

	@Override
	public List<Collection> findUnpurchasedCollectionsBySupplier(String supplierId) {
		Supplier supplier = (supplierId.isEmpty()) ? null : supplierDao.findOne(supplierId);
		if (supplier == null)return null;
		
		return collectionDao.findUnpurchasedCollectionsBySupplier(supplier);
	}

	

}
