package com.eca.backend.orderservice.config;

import com.eca.backend.orderservice.model.OrderDocument;
import com.eca.backend.orderservice.repository.OrderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initOrders(OrderRepository repository) {
        return args -> {
            try {
                if (repository.count() == 0) {
                    OrderDocument o1 = new OrderDocument();
                    o1.setUserId(1L);
                    o1.setStatus("CONFIRMED");
                    o1.setTotalAmount(new BigDecimal("199.99"));
                    o1.setShippingAddress("Colombo 07, Sri Lanka");
                    o1.setCreatedAt(LocalDateTime.now());

                    OrderDocument o2 = new OrderDocument();
                    o2.setUserId(2L);
                    o2.setStatus("DELIVERED");
                    o2.setTotalAmount(new BigDecimal("129.50"));
                    o2.setShippingAddress("Kandy, Sri Lanka");
                    o2.setCreatedAt(LocalDateTime.now());

                    repository.saveAll(List.of(o1, o2));
                }
            } catch (Exception e) {
                System.err.println("MongoDB DataInitializer skipped: " + e.getMessage());
            }
        };
    }
}
