package com.example.paydemo.controller;

import com.example.paydemo.dto.CreateOrderRequest;
import com.example.paydemo.dto.OrderQueryResponse;
import com.example.paydemo.entity.Order;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @PostMapping
    public Order createOrder(@RequestBody CreateOrderRequest request) {
        // 创建新订单
        Order order = new Order();
        order.setOrderId(UUID.randomUUID().toString());
        order.setUserId(request.getUserId());
        order.setAmount(request.getAmount());
        order.setStatus("CREATED");
        order.setCreateTime(LocalDateTime.now());
        
        return order;
    }
    
    // 根据订单ID查询订单
    @GetMapping("/{orderId}")
    public OrderQueryResponse getOrder(@PathVariable String orderId) {
        // TODO: 这里应该添加订单服务层的查询逻辑
        // 这里暂时返回模拟数据
        Order order = new Order();
        order.setOrderId(orderId);
        order.setUserId("test_user");
        order.setAmount(new BigDecimal("100"));
        order.setStatus("CREATED");
        order.setCreateTime(LocalDateTime.now());
        
        return new OrderQueryResponse(
            order.getOrderId(),
            order.getUserId(),
            order.getAmount(),
            order.getStatus(),
            order.getCreateTime()
        );
    }
    
    // 根据用户ID查询订单列表
    @GetMapping
    public List<OrderQueryResponse> getOrdersByUserId(@RequestParam(required = false) String userId) {
        // TODO: 这里应该添加订单服务层的查询逻辑
        // 这里暂时返回模拟数据
        List<OrderQueryResponse> orders = new ArrayList<>();
        if (userId != null) {
            orders.add(new OrderQueryResponse(
                    UUID.randomUUID().toString(),
                    userId,
                    new BigDecimal("100"),
                    "CREATED",
                    LocalDateTime.now()));
        }
        return orders;
    }
    
} 