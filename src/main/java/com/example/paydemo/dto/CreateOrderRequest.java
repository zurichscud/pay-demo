package com.example.paydemo.dto;

import java.math.BigDecimal;

public class CreateOrderRequest {
    private String userId;
    private BigDecimal amount;
    
    // getter 和 setter 方法
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
} 