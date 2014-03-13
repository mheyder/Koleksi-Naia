package com.koleksinaia.dao;

import org.springframework.data.repository.CrudRepository;

import com.koleksinaia.core.entity.Customer;

public interface CustomerDao extends CrudRepository<Customer, String> {

}
