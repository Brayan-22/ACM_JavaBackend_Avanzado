package com.acm.shippingservice.shared;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor @AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentProcessedEvent {
    private String eventType;
    private String orderId;
    private String transactionId;
    private Double amount;
    private LocalDateTime timestamp;
}
