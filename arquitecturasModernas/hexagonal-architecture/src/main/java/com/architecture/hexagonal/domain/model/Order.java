package com.architecture.hexagonal.domain.model;

import com.architecture.hexagonal.domain.exception.DomainException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Order {
    private final Long id;
    private OrderStatus status;
    private double totalAmount;
    private final Long userId;
    private final List<OrderItem> items;

    public Order(Long id, Long userId) {
        this.id = id;
        this.userId = userId;
        this.status = OrderStatus.CREATED;
        this.totalAmount = 0.0;
        this.items = new ArrayList<>();
    }

    // Constructor factory para reconstruir objeto desde base de datos sin disparar reglas iniciales
    public Order(Long id, Long userId, OrderStatus status, double totalAmount, List<OrderItem> items) {
        this.id = id;
        this.userId = userId;
        this.status = status;
        this.totalAmount = totalAmount;
        this.items = items;
    }

    public void addItem(Product product, int quantity) {
        if (!product.isActive()) {
            throw new DomainException("Product is inactive");
        }
        OrderItem item = new OrderItem(product.getId(), quantity, product.getPrice());
        this.items.add(item);
        this.recalculateTotal();
    }

    private void recalculateTotal() {
        this.totalAmount = this.items.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }

    public void cancel() {
        if (this.status == OrderStatus.PAID) {
            throw new DomainException("Cannot cancel a paid order");
        }
        this.status = OrderStatus.CANCELLED;
    }
}
