package com.acm.paymentservice.shared;

import lombok.*;

import java.time.LocalDateTime;

@Data @NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentProcessedEvent {
    @Builder.Default
    private String eventType = "PaymentProcessed";
    private String orderId;
    private String transactionId;
    private Double amount;
    private LocalDateTime timestamp;
}