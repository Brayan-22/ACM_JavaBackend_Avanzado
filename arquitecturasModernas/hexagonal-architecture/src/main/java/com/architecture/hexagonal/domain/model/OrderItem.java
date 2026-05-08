package com.architecture.hexagonal.domain.model;

import lombok.Getter;

@Getter
public class OrderItem {
    private Long id;
    private final Long productId;
    private final int quantity;
    private final double price;

    public OrderItem(Long productId, int quantity, double price) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    // Constructor factory
    public OrderItem(Long id, Long productId, int quantity, double price) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }
}
