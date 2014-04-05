package com.koleksinaia.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.koleksinaia.core.entity.Customer;
import com.koleksinaia.core.entity.Order;
import com.koleksinaia.core.entity.Supplier;
import com.koleksinaia.core.entity.helper.CustomerWithStatistic;
import com.koleksinaia.core.entity.helper.SuppplierWithStatistic;

public interface OrderDao extends PagingAndSortingRepository<Order, Integer> {

	Page<Order> findByDateBetween(Date startDate, Date endDate, Pageable pageable);
	
	Page<Order> findByCustomerAndDateBetween(Customer customer, Date startDate, Date endDate, Pageable pageable);
	
	Page<Order> findBySupplierAndDateBetween(Supplier supplier, Date startDate, Date endDate, Pageable pageable);
	
	Page<Order> findByCustomerAndSupplierAndDateBetween(Customer customer, Supplier supplier, Date startDate, Date endDate, Pageable pageable);
	
	List<Order> findByDropshipIsNullOrItemTypeIsNullAndCustomer(Customer customer);
	
	//NOTE the JPA query " @Query("select o from Order o") " cannot be used might because the entity name (Order) is reserved by the system 
	@Query(value = "SELECT * FROM myorder WHERE supplier_id = :supplierId AND (purchase_id IS NULL OR collection_id IS NULL)", nativeQuery = true)
	List<Order> findActiveOrdersBySupplier(@Param("supplierId") String supplierId);
	
	@Query("SELECT NEW com.koleksinaia.core.entity.helper.SuppplierWithStatistic(o.supplier, SUM(CASE WHEN o.collection.id IS NULL THEN 1 END)) FROM com.koleksinaia.core.entity.Order o GROUP BY o.supplier HAVING SUM(CASE WHEN o.collection.id IS NULL THEN 1 END) > 0")
	List<SuppplierWithStatistic> countUncollectedOrdersGroupBySupplier();
	
	@Query("SELECT NEW com.koleksinaia.core.entity.helper.SuppplierWithStatistic(o.supplier, SUM(CASE WHEN o.purchase.id IS NULL THEN 1 END)) FROM com.koleksinaia.core.entity.Order o GROUP BY o.supplier HAVING SUM(CASE WHEN o.purchase.id IS NULL THEN 1 END) > 0")
	List<SuppplierWithStatistic> countUnpurchasedOrdersGroupBySupplier();
	
	@Query("SELECT o FROM com.koleksinaia.core.entity.Order o WHERE o.supplier = :supplier AND o.collection.id IS NULL")
	List<Order> findUncollectedOrdersBySupplier(@Param("supplier") Supplier supplier);
	
	@Query("SELECT o FROM com.koleksinaia.core.entity.Order o WHERE o.supplier = :supplier AND o.purchase.id IS NULL")
	List<Order> findUnpurchasedOrdersBySupplier(@Param("supplier") Supplier supplier);

	@Query("SELECT NEW com.koleksinaia.core.entity.helper.CustomerWithStatistic(o.customer, SUM(CASE WHEN o.shipping.id IS NULL THEN 1 END)) FROM com.koleksinaia.core.entity.Order o WHERE o.collection.id IS NOT NULL AND o.purchase.id IS NOT NULL GROUP BY o.customer HAVING SUM(CASE WHEN o.shipping.id IS NULL THEN 1 END) > 0")
	List<CustomerWithStatistic> countUnshippedOrdersGroupByCustomer();
	
	@Query("SELECT NEW com.koleksinaia.core.entity.helper.CustomerWithStatistic(o.customer, SUM(CASE WHEN o.payment.id IS NULL THEN 1 END)) FROM com.koleksinaia.core.entity.Order o WHERE o.collection.id IS NOT NULL AND o.purchase.id IS NOT NULL GROUP BY o.customer HAVING SUM(CASE WHEN o.payment.id IS NULL THEN 1 END) > 0")
	List<CustomerWithStatistic> countUnpaidOrdersGroupByCustomer();

	@Query("SELECT o FROM com.koleksinaia.core.entity.Order o WHERE o.customer = :customer AND o.shipping.id IS NULL AND o.collection.id IS NOT NULL AND o.purchase.id IS NOT NULL")
	List<Order> findUnshippedOrdersOfCustomer(@Param("customer") Customer customer);

	@Query("SELECT o FROM com.koleksinaia.core.entity.Order o WHERE o.customer = :customer AND o.payment.id IS NULL AND o.collection.id IS NOT NULL AND o.purchase.id IS NOT NULL")
	List<Order> findUnpaidOrdersOfCustomer(@Param("customer") Customer customer);

	
}
