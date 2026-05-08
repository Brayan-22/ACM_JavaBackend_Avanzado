package com.acm.paymentservice.shared;

import lombok.*;

@Data
@NoArgsConstructor @AllArgsConstructor
public class OrderItemEvent {
    private String productId;
    private Integer quantity;
}