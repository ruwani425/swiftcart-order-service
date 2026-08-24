package com.eca.backend.orderservice.model;

import java.time.LocalDateTime;

public class OrderLog {

    private String status;
    private String message;
    private LocalDateTime timestamp;

    public OrderLog() {
        this.timestamp = LocalDateTime.now();
    }

    public OrderLog(String status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
