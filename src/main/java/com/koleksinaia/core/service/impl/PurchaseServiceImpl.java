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
import com.koleksinaia.core.entity.Purchase;
import com.koleksinaia.core.entity.Supplier;
import com.koleksinaia.core.service.PurchaseService;
import com.koleksinaia.dao.CollectionDao;
import com.koleksinaia.dao.OrderDao;
import com.koleksinaia.dao.PurchaseDao;
import com.koleksinaia.dao.SupplierDao;

@Service("purchaseService")
@Transactional(readOnly = true)
public class PurchaseServiceImpl implements PurchaseService {
	private static final int PAGE_SIZE = 20;
	private static final String ASC_SORT_DIRECTION = "oldest";
	private static final String DEFAULT_SORT_PROPERTY = "date";
	private static final long ONE_YEAR_IN_MILLISECONDS = 31556926000L;	
	
	@Resource
	private PurchaseDao purchaseDao;
	
	@Resource
	private OrderDao orderDao;
	
	@Resource
	private SupplierDao supplierDao;
	
	@Resource
	private CollectionDao collectionDao;

	@Override
	public Purchase findByPurchaseId(int id) {
		Purchase purchase = purchaseDao.findOne(id);
		purchase.getOrders().size();
		purchase.getCollections().size();
		return purchase;
	}

	@Override
	public List<Purchase> findAllPurchases() {
		return (List<Purchase>) purchaseDao.findAllAndFetchOrders();
	}
	
	@Override
	public Page<Purchase> findPurchases(int page, String sortDirection, Date startDate, Date endDate, String supplierId) {
		Date endDateRange = (endDate != null) ? endDate : new Date(java.util.Calendar.getInstance().getTime().getTime());
		Date startDateRange = (startDate != null) ? startDate : new Date(endDateRange.getTime() - ONE_YEAR_IN_MILLISECONDS);		
		Direction direction = (sortDirection.equalsIgnoreCase(ASC_SORT_DIRECTION)) ? Sort.Direction.ASC : Sort.Direction.DESC;
		Supplier supplier = (supplierId.isEmpty()) ? null : supplierDao.findOne(supplierId);
		
		PageRequest pageable = new PageRequest(page - 1 ,PAGE_SIZE,new Sort(new Sort.Order(direction, DEFAULT_SORT_PROPERTY)));
		if (supplier != null) {
			return purchaseDao.findBySupplierAndDateBetween(supplier, startDateRange, endDateRange, pageable);
		}
		return purchaseDao.findByDateBetween(startDateRange, endDateRange, pageable);
	}

	@Override
	@Transactional(readOnly = false)
	public Purchase savePurchase(Purchase newPurchase) {
		boolean isNewRecord = !purchaseDao.exists(newPurchase.getId());		
		Purchase purchase = (isNewRecord) ? new Purchase() : purchaseDao.findOne(newPurchase.getId());		
		if (isNewRecord) {
			// validate if supplier is exist
			if (supplierDao.exists(newPurchase.getSupplier().getId())) {
				purchase.setSupplier(supplierDao.findOne(newPurchase.getSupplier().getId()));
			} else {
				return null;
			}
		} else {
			for (Order order : purchase.getOrders()) {
				order.setPurchase(null);
			}
			purchase.getOrders().clear();
			for (Collection collection : purchase.getCollections()) {
				collection.setPurchase(null);
			}
		}
		
		purchase.setDate(newPurchase.getDate());
		purchase.setInfo(newPurchase.getInfo());
		purchase.setOtherCost(newPurchase.getOtherCost());
		
		newPurchase.setOrderCost(0);
		List<Order> orderIds = new ArrayList<Order>(newPurchase.getOrders());
		List<Order> orders = new ArrayList<Order>();
		for(int i=0; i<orderIds.size(); i++) {
			// validate if order is exist
			if (!orderDao.exists(orderIds.get(i).getId())) return null;
			Order order = orderDao.findOne(orderIds.get(i).getId());
			// validate if order is belong to the supplier
			if (!order.getSupplier().getId().equalsIgnoreCase(newPurchase.getSupplier().getId())) return null;
			order.setPurchase(purchase);
			orders.add(order);
			newPurchase.setOrderCost(newPurchase.getOrderCost() + order.getCost());
		}
		purchase.setOrderCount(orders.size());
		purchase.setOrderCost(newPurchase.getOrderCost());
		purchase.setOrders(new HashSet<Order>(orders));
		
		newPurchase.setCollectionCost(0);
		List<Collection> collectionIds = new ArrayList<Collection>(newPurchase.getCollections());
		List<Collection> collections = new ArrayList<Collection>();
		for(int i=0; i<collectionIds.size(); i++) {
			// validate if collection is exist
			if (!collectionDao.exists(collectionIds.get(i).getId())) return null;
			Collection coll = collectionDao.findOne(collectionIds.get(i).getId());
			// validate if order is belong to the supplier
			if (!coll.getSupplier().getId().equalsIgnoreCase(newPurchase.getSupplier().getId())) return null;
			coll.setPurchase(purchase);
			collections.add(coll);
			newPurchase.setCollectionCost(newPurchase.getCollectionCost() + coll.getCost());
		}
		purchase.setCollectionCost(newPurchase.getCollectionCost());
		purchase.setCollections(new HashSet<Collection>(collections));

		purchase.setTotalCost(purchase.getOrderCost() + purchase.getCollectionCost() + purchase.getOtherCost());
		
		return purchaseDao.save(purchase);
	}

	@Override
	public boolean deletePurchase(int id) {
		if (purchaseDao.exists(id)) {
			purchaseDao.delete(id);
			return !purchaseDao.exists(id);
		}
		return false;
	}
	
	@Override
	public boolean purchaseIdExists(int id) {
		return purchaseDao.exists(id);
	}

}
