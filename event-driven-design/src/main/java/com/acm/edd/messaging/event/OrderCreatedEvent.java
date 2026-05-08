package com.acm.edd.messaging.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent {

    private String eventId; // Identificador único del evento para idempotencia
    private Long orderId;
    private String customerId;
    private LocalDateTime createdAt;
    
}
