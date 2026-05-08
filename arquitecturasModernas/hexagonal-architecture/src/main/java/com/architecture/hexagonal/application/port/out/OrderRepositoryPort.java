package com.architecture.hexagonal.application.port.out;

import com.architecture.hexagonal.domain.model.Order;
import java.util.Optional;

public interface OrderRepositoryPort {
    Order save(Order order);
    Optional<Order> findById(Long id);
}
