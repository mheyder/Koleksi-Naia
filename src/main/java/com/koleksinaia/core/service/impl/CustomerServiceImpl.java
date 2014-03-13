package com.koleksinaia.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.koleksinaia.core.entity.Customer;
import com.koleksinaia.core.service.CustomerService;
import com.koleksinaia.dao.CustomerDao;

@Service("customerService")
@Transactional(readOnly = true)
public class CustomerServiceImpl implements CustomerService {

	@Resource
	private CustomerDao customerDao;
	
	@Override
	public Customer findByCustomerId(String id) {
		return customerDao.findOne(id);
	}

	@Override
	public boolean customerIdExists(String id) {
		return customerDao.exists(id);
	}

	@Override
	public List<Customer> findAllCustomers() {
		return (List<Customer>) customerDao.findAll();
	}

	@Override
	@Transactional(readOnly = false)
	public Customer saveCustomer(Customer customer) {
		return customerDao.save(customer);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean deleteCustomer(String id) {
		if (customerDao.exists(id)) {
			customerDao.delete(id);
			return !customerDao.exists(id);
		}
		return false;
	}

}
