package com.bala.miniecom.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bala.miniecom.exception.InvalidStatusFoundException;
import com.bala.miniecom.exception.OrderNotFoundException;
import com.bala.miniecom.model.Order;
import com.bala.miniecom.model.OrderLine;
import com.bala.miniecom.model.OrderStatus;
import com.bala.miniecom.model.PaymentType;
import com.bala.miniecom.repository.OrderRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    
    private static final Logger log =
            LoggerFactory.getLogger(OrderService.class);

    // 🔹 Constructor Injection (required)
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order saveOrder(Order order) {
    	log.info("Creating order enter : {}", order);
    	order.setOrderNo(generateOrderNo());
    	order.setStatus(OrderStatus.CONFIRMED);
    	 
    	  // 🔥 THIS IS THE MOST IMPORTANT PART
    	  if (order.getItems() != null) {
    	        for (OrderLine line : order.getItems()) {
    	            line.setOrder(order); // 🔥 FK owner set
    	        }
    	    }
    	  log.info("Creating order exit : {}", order);

        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
            .orElseThrow(() -> new OrderNotFoundException(id.toString()));
    }
    
    @Override
    public Order getOrder(String orderNo) {
	    return orderRepository.findByOrderNo(orderNo)
	            .orElseThrow(() ->
	                new RuntimeException("Order not found"));
	}

	
    @Override
    public Order updateStatus(String orderNo, OrderStatus newStatus) {

        Order order = orderRepository.findByOrderNo(orderNo)
                .orElseThrow(() -> new OrderNotFoundException(orderNo));

        if (!isValidTransition(order.getStatus(), newStatus)) {
            throw new InvalidStatusFoundException(
                    order.getStatus(),
                    newStatus
            );
        }

        order.setStatus(newStatus);

        // ✅ SAVE FIRST
        Order savedOrder = orderRepository.save(order);

        // 🔥 If delivered → delete after 5 minutes
        if (newStatus == OrderStatus.DELIVERED) {

            new Thread(() -> {
                try {
                    Thread.sleep(5 * 60 * 1000); // 5 minutes

                    orderRepository.delete(savedOrder);

                    log.info("Order auto deleted: {}", orderNo);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

        return savedOrder;
    }
    
    private String generateOrderNo() {
        return "ORD-" + System.currentTimeMillis();
    }
	
	private boolean isValidTransition(OrderStatus current, OrderStatus next) {

	        return switch (current) {
				case CONFIRMED ->
			            next == OrderStatus.PACKED
			         || next == OrderStatus.CANCELLED;
			
			    case PACKED ->
			            next == OrderStatus.SHIPPED
			         || next == OrderStatus.CANCELLED;
			
			    case SHIPPED ->
			            next == OrderStatus.DELIVERED;
			
			    default -> false;
	        };
	    }

	@Override
	public void deleteOrder(String orderNo) {

	    Order order = orderRepository.findByOrderNo(orderNo)
	            .orElseThrow(() -> new RuntimeException("Order not found"));

	    orderRepository.delete(order);
	}

	@Override
	public List<Order> getOrderByCustomerId(String customerId) {
	    return orderRepository.findByCustomerId(customerId);
	}
	
	
	
}
