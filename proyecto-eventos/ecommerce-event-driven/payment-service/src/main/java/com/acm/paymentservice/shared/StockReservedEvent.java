package com.acm.paymentservice.shared;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class StockReservedEvent {
    private String eventType;
    private String orderId;
    private List<OrderItemEvent> items;
    private LocalDateTime timestamp;
}