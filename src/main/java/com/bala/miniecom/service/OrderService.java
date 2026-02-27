package com.bala.miniecom.service;

import java.util.List;

import com.bala.miniecom.model.Order;
import com.bala.miniecom.model.OrderStatus;

public interface OrderService {
	Order saveOrder(Order order);
	List<Order> getAllOrders();
	Order getOrderById(Long id);
	List<Order> getOrderByCustomerId(String customerId);
	Order updateStatus(String id ,OrderStatus newStatus);
	Order getOrder(String orderNo);
	void deleteOrder(String orderNo);

}
