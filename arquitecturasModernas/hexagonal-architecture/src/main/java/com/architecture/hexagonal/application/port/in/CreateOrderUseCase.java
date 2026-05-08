package com.architecture.hexagonal.application.port.in;

import java.util.List;

public interface CreateOrderUseCase {

    OrderDto createOrder(OrderCommand command);

    record OrderCommand(Long userId, List<ItemCommand> items) {}
    record ItemCommand(Long productId, int quantity) {}
    record OrderDto(Long orderId, String status, double totalAmount) {}
}
