package com.bala.miniecom.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bala.miniecom.model.Order;
import com.bala.miniecom.model.OrderStatus;
import com.bala.miniecom.service.OrderService;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin
public class OrderController {

    private final OrderService orderService;

    // Constructor Injection
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // ✅ Create Order
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order saved = orderService.saveOrder(order);
        return ResponseEntity.ok(saved);
    }
    
    

    // ✅ Get All Orders
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    // ✅ Get Order by ID
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }
    @PutMapping("/{orderNo}/status")
    public ResponseEntity<Order>updateOrderStatus(
        @PathVariable String orderNo,
        @RequestParam OrderStatus status) {

    Order updated = orderService.updateStatus(orderNo, status);
    return ResponseEntity.ok(updated);
    
    }
    
    @GetMapping("/{orderNo}/status")
    public ResponseEntity<Order> getOrder(
            @PathVariable String orderNo) {

        Order order = orderService.getOrder(orderNo);

        return ResponseEntity.ok(order);
    }
    
    @DeleteMapping("/{orderNo}")
    public ResponseEntity<String> deleteOrder(
            @PathVariable String orderNo) {

        orderService.deleteOrder(orderNo);
        return ResponseEntity.ok("Order deleted successfully");
    }
    
    @GetMapping("/customerId/{customerId}")
    public List<Order> getOrderfromCustomerId(
            @PathVariable String customerId) {

        return orderService.getOrderByCustomerId(customerId);
    }
}
