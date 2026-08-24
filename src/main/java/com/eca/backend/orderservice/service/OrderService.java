package com.eca.backend.orderservice.service;

import com.eca.backend.orderservice.model.OrderDocument;
import com.eca.backend.orderservice.model.OrderLog;
import com.eca.backend.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CompletableFuture;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final List<OrderDocument> inMemoryOrders = new CopyOnWriteArrayList<>();

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderDocument> getAllOrders() {
        try {
            List<OrderDocument> dbOrders = orderRepository.findAll();
            if (dbOrders != null && !dbOrders.isEmpty()) {
                return dbOrders;
            }
        } catch (Exception ignored) {
        }
        return inMemoryOrders;
    }

    public Optional<OrderDocument> getOrderById(String id) {
        try {
            Optional<OrderDocument> dbOrder = orderRepository.findById(id);
            if (dbOrder.isPresent()) {
                return dbOrder;
            }
        } catch (Exception ignored) {
        }
        return inMemoryOrders.stream().filter(o -> id.equals(o.getId())).findFirst();
    }

    public List<OrderDocument> getOrdersByUserId(Long userId) {
        try {
            List<OrderDocument> dbOrders = orderRepository.findByUserId(userId);
            if (dbOrders != null && !dbOrders.isEmpty()) {
                return dbOrders;
            }
        } catch (Exception ignored) {
        }
        return inMemoryOrders.stream().filter(o -> userId.equals(o.getUserId())).toList();
    }

    public OrderDocument createOrder(OrderDocument order) {
        if (order.getId() == null || order.getId().isBlank()) {
            order.setId(UUID.randomUUID().toString().replace("-", "").substring(0, 16));
        }
        if (order.getStatus() == null || order.getStatus().isBlank()) {
            order.setStatus("PROCESSING");
        }
        
        order.getOrderLogs().add(new OrderLog(order.getStatus(), "Order document created successfully."));
        
        // Save to in-memory list for instant, zero-latency response
        inMemoryOrders.add(0, order);

        // Attempt async persistence to MongoDB if available
        CompletableFuture.runAsync(() -> {
            try {
                orderRepository.save(order);
            } catch (Exception ignored) {
            }
        });

        return order;
    }

    public OrderDocument updateOrderStatus(String id, String newStatus, String logNote) {
        OrderDocument order = getOrderById(id)
                .orElseGet(() -> {
                    OrderDocument fallback = new OrderDocument();
                    fallback.setId(id);
                    return fallback;
                });

        order.setStatus(newStatus);
        order.getOrderLogs().add(new OrderLog(newStatus, logNote != null ? logNote : "Status updated to " + newStatus));

        // Update in-memory if present
        int index = inMemoryOrders.indexOf(order);
        if (index >= 0) {
            inMemoryOrders.set(index, order);
        } else {
            inMemoryOrders.add(0, order);
        }

        CompletableFuture.runAsync(() -> {
            try {
                orderRepository.save(order);
            } catch (Exception ignored) {
            }
        });

        return order;
    }
}
