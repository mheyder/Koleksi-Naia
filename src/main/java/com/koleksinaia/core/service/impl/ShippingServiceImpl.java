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

import com.koleksinaia.core.entity.Customer;
import com.koleksinaia.core.entity.Order;
import com.koleksinaia.core.entity.Shipping;
import com.koleksinaia.core.service.ShippingService;
import com.koleksinaia.dao.CustomerDao;
import com.koleksinaia.dao.OrderDao;
import com.koleksinaia.dao.ShippingDao;

@Service("shippingService")
@Transactional(readOnly = true)
public class ShippingServiceImpl implements ShippingService {
	private static final int PAGE_SIZE = 20;
	private static final String ASC_SORT_DIRECTION = "oldest";
	private static final String DEFAULT_SORT_PROPERTY = "date";
	private static final long ONE_YEAR_IN_MILLISECONDS = 31556926000L;
	
	@Resource
	private ShippingDao shippingDao;
	
	@Resource
	private CustomerDao customerDao;
	
	@Resource
	private OrderDao orderDao;

	@Override
	public Shipping findByShippingId(int id) {
		Shipping shipping = shippingDao.findOne(id);
		shipping.getOrders().size();
		return shipping;
	}

	@Override
	public boolean shippingIdExists(int id) {
		return shippingDao.exists(id);
	}

	@Override
	public Page<Shipping> findShippings(int page, String sortDirection, Date startDate, Date endDate, String customerId) {
		Date endDateRange = (endDate != null) ? endDate : new Date(java.util.Calendar.getInstance().getTime().getTime());
		Date startDateRange = (startDate != null) ? startDate : new Date(endDateRange.getTime() - ONE_YEAR_IN_MILLISECONDS);		
		Direction direction = (sortDirection.equalsIgnoreCase(ASC_SORT_DIRECTION)) ? Sort.Direction.ASC : Sort.Direction.DESC;
		Customer customer = (customerId.isEmpty()) ? null : customerDao.findOne(customerId);
		
		PageRequest pageable = new PageRequest(page - 1 ,PAGE_SIZE,new Sort(new Sort.Order(direction, DEFAULT_SORT_PROPERTY)));
		if (customer != null) {
			return shippingDao.findByCustomerAndDateBetween(customer, startDateRange, endDateRange, pageable);
		}
		return shippingDao.findByDateBetween(startDateRange, endDateRange, pageable);
	}

	@Override
	@Transactional(readOnly = false)
	public Shipping saveShipping(Shipping newShipping) {
		boolean isNewRecord = !shippingDao.exists(newShipping.getId());		
		Shipping shipping = (isNewRecord) ? new Shipping() : shippingDao.findOne(newShipping.getId());		
		if (isNewRecord) {
			// validate if supplier is exist
			if (customerDao.exists(newShipping.getCustomer().getId())) {
				shipping.setCustomer(customerDao.findOne(newShipping.getCustomer().getId()));
			} else {
				return null;
			}
		} else {
			for (Order order : shipping.getOrders()) {
				order.setShipping(null);
			}
			shipping.getOrders().clear();
		}
		
		shipping.setDate(newShipping.getDate());
		shipping.setPrice(newShipping.getPrice());
		shipping.setDropship(newShipping.getDropship());
		shipping.setAddress(newShipping.getAddress());
		shipping.setInfo(newShipping.getInfo());		

		List<Order> orderIdList = new ArrayList<Order>(newShipping.getOrders());
		List<Order> newOrders = new ArrayList<Order>();
		for(int i=0; i<orderIdList.size(); i++) {
			// validate if order is exist
			if (!orderDao.exists(orderIdList.get(i).getId())) return null;
			Order order = orderDao.findOne(orderIdList.get(i).getId());
			// validate if order is belong to the supplier
			if (!order.getCustomer().getId().equalsIgnoreCase(newShipping.getCustomer().getId())) return null;
			order.setShipping(shipping);
			newOrders.add(order);
		}
		shipping.setOrders(new HashSet<Order>(newOrders));
		shipping.setOrderCount(newShipping.getOrders().size());

		return shippingDao.save(shipping);
	}

	@Override
	public boolean deleteShipping(String id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public List<Shipping> findUnpaidShippingsByCustomer(String customerId) {
		Customer customer = (customerId.isEmpty()) ? null : customerDao.findOne(customerId);
		if (customer == null)return null;
		
		return shippingDao.findUnpaidShippingByCustomer(customer);
	}

}
