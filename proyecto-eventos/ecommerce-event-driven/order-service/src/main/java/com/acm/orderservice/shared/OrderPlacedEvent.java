package com.acm.orderservice.shared;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
public class OrderPlacedEvent {
    private String eventType = "OrderPlaced";
    private String orderId;
    private String customerId;
    private List<OrderItemEvent> items;
    private Instant timestamp = Instant.now();
    
    public OrderPlacedEvent(String orderId, String customerId, List<OrderItemEvent> items) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.items = items;
    }
}