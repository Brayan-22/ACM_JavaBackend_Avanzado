package com.architecture.hexagonal.application.port.in;

public interface CancelOrderUseCase {
    void cancelOrder(Long orderId);
}
