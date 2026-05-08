package com.acm.shippingservice.shared;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShipmentScheduledEvent {
    @Builder.Default
    private String eventType = "ShipmentScheduled";
    private String orderId;
    private String trackingNumber;
    private LocalDateTime timestamp;
}
