package com.bala.miniecom.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bala.miniecom.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	 Optional<Order> findByOrderNo(String orderNo);
	 
	 List<Order> findByCustomerId(String customerId);
	 
}