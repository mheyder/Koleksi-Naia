package com.koleksinaia.core.service.impl;

import java.sql.Date;
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
import com.koleksinaia.core.entity.Supplier;
import com.koleksinaia.core.service.OrderService;
import com.koleksinaia.dao.CustomerDao;
import com.koleksinaia.dao.OrderDao;
import com.koleksinaia.dao.SupplierDao;

@Service("orderService")
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {
	private static final int PAGE_SIZE = 20;
	private static final String ASC_SORT_DIRECTION = "oldest";
	private static final String DEFAULT_SORT_PROPERTY = "date";
	private static final long ONE_YEAR_IN_MILLISECONDS = 31556926000L;
	
	//private static Logger LOG = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Resource
	private OrderDao orderDao;
	
	@Resource
	private SupplierDao supplierDao;
	
	@Resource
	private CustomerDao customerDao;
	
	@Override
	public Order findByOrderId(int id) {
		return orderDao.findOne(id);
	}

	@Override
	public boolean orderIdExists(int id) {
		return orderDao.exists(id);
	}

	@Override
	public List<Order> findAllOrders() {		
		return (List<Order>) orderDao.findAll();
	}

	@Override
	@Transactional(readOnly = false)
	public Order saveOrder(Order order) {
		
		order.setSupplier(supplierDao.findOne(order.getSupplier().getId()));
		order.setCustomer(customerDao.findOne(order.getCustomer().getId()));
		return orderDao.save(order);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean saveOrders(List<Order> orders) {
		//TODO add validation
		for(int i=0; i<orders.size(); i++) {
			saveOrder(orders.get(i));
		}
		return true;
	}

	@Override
	@Transactional(readOnly = false)
	public boolean deleteOrder(int id) {
		if (orderDao.exists(id)) {
			orderDao.delete(id);
			return !orderDao.exists(id);
		}
		return false;
	}

	@Override
	public Page<Order> customSearch(int page, String sortDirection, Date startDate, Date endDate, String customerId, String supplierId) {
//		java.util.Calendar cal = java.util.Calendar.getInstance();
//		java.util.Date utilDate = cal.getTime();
//		long s = 1393954585000L;
//		long e = 1395164185000L;
//		
//		Date startDate = new Date(s);
//		Date endDate = new Date(e);		
//		String sortDirection = "newest";
//		String customerID = "ati";
//		String supplierID = "sr";
//		int page = 1;

		Date endDateRange = (endDate != null) ? endDate : new Date(java.util.Calendar.getInstance().getTime().getTime());
		Date startDateRange = (startDate != null) ? startDate : new Date(endDateRange.getTime() - ONE_YEAR_IN_MILLISECONDS);		
		Direction direction = (sortDirection.equalsIgnoreCase(ASC_SORT_DIRECTION)) ? Sort.Direction.ASC : Sort.Direction.DESC;
		Customer customer = (customerId.isEmpty()) ? null : customerDao.findOne(customerId);
		Supplier supplier = (supplierId.isEmpty()) ? null : supplierDao.findOne(supplierId);
		
		PageRequest pageable = new PageRequest(page - 1 ,PAGE_SIZE,new Sort(new Sort.Order(direction, DEFAULT_SORT_PROPERTY)));
		
		if (customer == null && supplier == null) {
			return orderDao.findByDateBetween(startDateRange, endDateRange, pageable);
		} else if (customer != null && supplier == null) {
			return orderDao.findByCustomerAndDateBetween(customer, startDateRange, endDateRange, pageable);
		} else if (customer == null && supplier != null) {
			return orderDao.findBySupplierAndDateBetween(supplier, startDateRange, endDateRange, pageable);
		} else { //customer != null && supplier != null
			return orderDao.findByCustomerAndSupplierAndDateBetween(customer, supplier, startDateRange, endDateRange, pageable);
		}
		
	}

	@Override
	public List<Order> findAllActiveOrdersBySupplier(String supplierId) {
		return orderDao.findActiveOrdersBySupplier(supplierId);
	}

	@Override
	public List<Order> findUncollectedOrdersBySupplier(String supplierId) {
		Supplier supplier = (supplierId.isEmpty()) ? null : supplierDao.findOne(supplierId);
		if (supplier == null)return null;
		
		return orderDao.findUncollectedOrdersBySupplier(supplier);
	}

	@Override
	public List<Order> findUnpurchasedOrdersBySupplier(String supplierId) {
		Supplier supplier = (supplierId.isEmpty()) ? null : supplierDao.findOne(supplierId);
		if (supplier == null)return null;
		
		return orderDao.findUnpurchasedOrdersBySupplier(supplier);
	}

	@Override
	public List<Order> findUnshippedOrdersOfCustomer(String customerId) {
		Customer customer = (customerId.isEmpty()) ? null : customerDao.findOne(customerId);
		if (customer == null)return null;
		
		return orderDao.findUnshippedOrdersOfCustomer(customer);
	}

	@Override
	public List<Order> findUnpaidOrdersOfCustomer(String customerId) {
		Customer customer = (customerId.isEmpty()) ? null : customerDao.findOne(customerId);
		if (customer == null)return null;
		
		return orderDao.findUnpaidOrdersOfCustomer(customer);
	}
}
