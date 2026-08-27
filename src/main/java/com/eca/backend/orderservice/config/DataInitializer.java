package com.eca.backend.orderservice.config;

import com.eca.backend.orderservice.model.OrderDocument;
import com.eca.backend.orderservice.repository.OrderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
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
                    o1.setTotalPrice(199.99);
                    o1.setNote("Express delivery requested");
                    o1.setCreatedAt(new Date());

                    OrderDocument o2 = new OrderDocument();
                    o2.setUserId(2L);
                    o2.setStatus("DELIVERED");
                    o2.setTotalPrice(129.50);
                    o2.setNote("Left at front door");
                    o2.setCreatedAt(new Date());

                    repository.saveAll(List.of(o1, o2));
                }
            } catch (Exception e) {
                // Mongodb connection fallback safely logged
                System.err.println("MongoDB DataInitializer skipped: " + e.getMessage());
            }
        };
    }
}
