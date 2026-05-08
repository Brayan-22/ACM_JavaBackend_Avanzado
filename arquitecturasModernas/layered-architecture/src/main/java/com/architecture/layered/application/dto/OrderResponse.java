package com.architecture.layered.application.dto;

import com.architecture.layered.domain.model.Order;

public record OrderResponse(Long id, String status, double totalAmount) {
    public static OrderResponse from(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getStatus().name(),
                order.getTotalAmount()
        );
    }
}
