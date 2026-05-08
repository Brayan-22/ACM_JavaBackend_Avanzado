package com.architecture.layered.application.dto;

import java.util.List;

public record OrderCreateRequest(Long userId, List<OrderItemRequest> items) {
    public record OrderItemRequest(Long productId, int quantity) {}
}
