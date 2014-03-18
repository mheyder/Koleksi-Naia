package com.koleksinaia.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.annotations.Sort;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.koleksinaia.core.entity.Order;
import com.koleksinaia.core.entity.Supplier;
import com.koleksinaia.core.service.OrderService;
import com.koleksinaia.dao.CustomerDao;
import com.koleksinaia.dao.OrderDao;
import com.koleksinaia.dao.SupplierDao;
import com.koleksinaia.rest.controller.SupplierController;

@Service("orderService")
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {
	private static final int PAGE_SIZE = 20;
	
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
	public Page<Order> customSearch() {
		// TODO Auto-generated method stub
		return null;
	}


}
