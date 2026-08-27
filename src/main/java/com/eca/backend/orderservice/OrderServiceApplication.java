package com.eca.backend.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {
    "com.eca.backend.orderservice.controller",
    "com.eca.backend.orderservice.service",
    "com.eca.backend.orderservice.repository",
    "com.eca.backend.orderservice.config"
})
@EnableMongoRepositories(basePackages = "com.eca.backend.orderservice.repository")
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}
