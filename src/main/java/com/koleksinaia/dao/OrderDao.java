package com.koleksinaia.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.koleksinaia.core.entity.Order;

public interface OrderDao extends PagingAndSortingRepository<Order, Integer> {

}
