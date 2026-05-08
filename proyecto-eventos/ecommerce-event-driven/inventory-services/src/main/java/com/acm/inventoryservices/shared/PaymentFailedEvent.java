package com.acm.inventoryservices.shared;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentFailedEvent {
    private String eventType = "PaymentFailed";
    private String orderId;
    private String trace;
    private LocalDateTime timestamp;
}