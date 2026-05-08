package com.acm.inventoryservices.shared;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutOfStockEvent {
    private String eventType = "OutOfStock";
    private String orderId;
    private String trace;
    private LocalDateTime timestamp;
}