package com.acm.inventoryservices.shared;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPlacedEvent {
    private String eventType;
    private String orderId;
    private String customerId;
    private List<OrderItem> items;
    private LocalDateTime timestamp;
}
