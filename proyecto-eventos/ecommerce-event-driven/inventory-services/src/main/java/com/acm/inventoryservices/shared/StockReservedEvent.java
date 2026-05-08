package com.acm.inventoryservices.shared;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockReservedEvent {
    private String eventType = "StockReserved";
    private String orderId;
    private List<OrderItem> items;
    private LocalDateTime timestamp;
}