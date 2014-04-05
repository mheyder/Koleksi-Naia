package com.koleksinaia.core.service;

import java.sql.Date;

import org.springframework.data.domain.Page;

import com.koleksinaia.core.entity.Payment;

public interface PaymentService {

	Payment findByPaymentId(int id);
	 
	Payment savePayment(Payment payment);
	    
	boolean deletePayment(int id);

	Page<Payment> findPayments(int page, String sortDirection, Date startDate, Date endDate, String customerId);

	boolean paymentIdExists(int id);
}
