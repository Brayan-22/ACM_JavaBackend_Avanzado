package com.acm.paymentservice.shared;

import lombok.*;

import java.time.LocalDateTime;

@Data @NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentFailedEvent {
    @Builder.Default
    private String eventType = "PaymentFailed";
    private String orderId;
    private String trace;
    private LocalDateTime timestamp;
}