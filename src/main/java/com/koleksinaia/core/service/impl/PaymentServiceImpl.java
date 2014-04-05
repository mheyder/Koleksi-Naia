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
import com.koleksinaia.core.entity.Payment;
import com.koleksinaia.core.entity.Shipping;
import com.koleksinaia.core.service.PaymentService;
import com.koleksinaia.dao.ShippingDao;
import com.koleksinaia.dao.OrderDao;
import com.koleksinaia.dao.PaymentDao;
import com.koleksinaia.dao.CustomerDao;

@Service("paymentService")
@Transactional(readOnly = true)
public class PaymentServiceImpl implements PaymentService{
	private static final int PAGE_SIZE = 20;
	private static final String ASC_SORT_DIRECTION = "oldest";
	private static final String DEFAULT_SORT_PROPERTY = "date";
	private static final long ONE_YEAR_IN_MILLISECONDS = 31556926000L;	
	
	@Resource
	private PaymentDao paymentDao;
	
	@Resource
	private OrderDao orderDao;
	
	@Resource
	private CustomerDao customerDao;
	
	@Resource
	private ShippingDao shippingDao;

	@Override
	public Payment findByPaymentId(int id) {
		Payment payment = paymentDao.findOne(id);
		payment.getOrders().size();
		payment.getShippings().size();
		return payment;
	}

	@Override
	@Transactional(readOnly = false)
	public Payment savePayment(Payment newPayment) {
		boolean isNewRecord = !paymentDao.exists(newPayment.getId());		
		Payment payment = (isNewRecord) ? new Payment() : paymentDao.findOne(newPayment.getId());		
		if (isNewRecord) {
			// validate if supplier is exist
			if (customerDao.exists(newPayment.getCustomer().getId())) {
				payment.setCustomer(customerDao.findOne(newPayment.getCustomer().getId()));
			} else {
				return null;
			}
		} else {
			for (Order order : payment.getOrders()) {
				order.setPayment(null);
			}
			payment.getOrders().clear();
			for (Shipping shipping : payment.getShippings()) {
				shipping.setPayment(null);
			}
		}
		
		payment.setDate(newPayment.getDate());
		payment.setInfo(newPayment.getInfo());
		payment.setOtherPrice(newPayment.getOtherPrice());
		
		newPayment.setOrderPrice(0);
		List<Order> orderIds = new ArrayList<Order>(newPayment.getOrders());
		List<Order> orders = new ArrayList<Order>();
		for(int i=0; i<orderIds.size(); i++) {
			// validate if order is exist
			if (!orderDao.exists(orderIds.get(i).getId())) return null;
			Order order = orderDao.findOne(orderIds.get(i).getId());
			// validate if order is belong to the supplier
			if (!order.getCustomer().getId().equalsIgnoreCase(newPayment.getCustomer().getId())) return null;
			order.setPayment(payment);
			orders.add(order);
			newPayment.setOrderPrice(newPayment.getOrderPrice() + order.getPrice());
		}
		payment.setOrderCount(orders.size());
		payment.setOrderPrice(newPayment.getOrderPrice());
		payment.setOrders(new HashSet<Order>(orders));
		
		newPayment.setShippingPrice(0);
		List<Shipping> shippingIds = new ArrayList<Shipping>(newPayment.getShippings());
		List<Shipping> shippings = new ArrayList<Shipping>();
		for(int i=0; i<shippingIds.size(); i++) {
			// validate if collection is exist
			if (!shippingDao.exists(shippingIds.get(i).getId())) return null;
			Shipping ship = shippingDao.findOne(shippingIds.get(i).getId());
			// validate if order is belong to the supplier
			if (!ship.getCustomer().getId().equalsIgnoreCase(newPayment.getCustomer().getId())) return null;
			ship.setPayment(payment);
			shippings.add(ship);
			newPayment.setShippingPrice(newPayment.getShippingPrice() + ship.getPrice());
		}
		payment.setShippingPrice(newPayment.getShippingPrice());
		payment.setShippings(new HashSet<Shipping>(shippings));

		payment.setTotalPrice(payment.getOrderPrice() + payment.getShippingPrice() + payment.getOtherPrice());
		
		return paymentDao.save(payment);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean deletePayment(int id) {
		if (paymentDao.exists(id)) {
			paymentDao.delete(id);
			return !paymentDao.exists(id);
		}
		return false;
	}

	@Override
	public Page<Payment> findPayments(int page, String sortDirection, Date startDate, Date endDate, String customerId) {
		Date endDateRange = (endDate != null) ? endDate : new Date(java.util.Calendar.getInstance().getTime().getTime());
		Date startDateRange = (startDate != null) ? startDate : new Date(endDateRange.getTime() - ONE_YEAR_IN_MILLISECONDS);		
		Direction direction = (sortDirection.equalsIgnoreCase(ASC_SORT_DIRECTION)) ? Sort.Direction.ASC : Sort.Direction.DESC;
		Customer customer = (customerId.isEmpty()) ? null : customerDao.findOne(customerId);
		
		PageRequest pageable = new PageRequest(page - 1 ,PAGE_SIZE,new Sort(new Sort.Order(direction, DEFAULT_SORT_PROPERTY)));
		if (customer != null) {
			return paymentDao.findByCustomerAndDateBetween(customer, startDateRange, endDateRange, pageable);
		}
		return paymentDao.findByDateBetween(startDateRange, endDateRange, pageable);
	}

	@Override
	public boolean paymentIdExists(int id) {
		return paymentDao.exists(id);
	}

}
