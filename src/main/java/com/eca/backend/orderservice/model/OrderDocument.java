package com.eca.backend.orderservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "orders")
public class OrderDocument {

    @Id
    private String id;
    private Long userId;
    private List<OrderItem> items = new ArrayList<>();
    private BigDecimal totalAmount;
    private String status;
    private String shippingAddress;
    private List<OrderLog> orderLogs = new ArrayList<>();
    private LocalDateTime createdAt;

    public OrderDocument() {
        this.createdAt = LocalDateTime.now();
        this.status = "CREATED";
    }

    public OrderDocument(String id, Long userId, List<OrderItem> items, BigDecimal totalAmount, String status, String shippingAddress) {
        this.id = id;
        this.userId = userId;
        this.items = items != null ? items : new ArrayList<>();
        this.totalAmount = totalAmount;
        this.status = status != null ? status : "CREATED";
        this.shippingAddress = shippingAddress;
        this.createdAt = LocalDateTime.now();
        this.orderLogs.add(new OrderLog("CREATED", "Order created successfully."));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public List<OrderLog> getOrderLogs() {
        return orderLogs;
    }

    public void setOrderLogs(List<OrderLog> orderLogs) {
        this.orderLogs = orderLogs;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
