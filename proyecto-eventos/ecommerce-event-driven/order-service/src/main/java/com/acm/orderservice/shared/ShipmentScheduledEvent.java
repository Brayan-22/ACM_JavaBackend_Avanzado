package com.acm.orderservice.shared;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShipmentScheduledEvent {
    @Builder.Default
    private String eventType = "ShipmentScheduled";
    private String orderId;
    private String trackingNumber;
    private LocalDateTime timestamp;
}
