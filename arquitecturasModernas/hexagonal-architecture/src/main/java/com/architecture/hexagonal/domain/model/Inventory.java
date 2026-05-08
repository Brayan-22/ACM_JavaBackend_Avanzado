package com.architecture.hexagonal.domain.model;

import com.architecture.hexagonal.domain.exception.DomainException;
import lombok.Getter;

@Getter
public class Inventory {
    private final Long id;
    private final Long productId;
    private int quantity;

    public Inventory(Long id, Long productId, int quantity) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
    }

    public void decrease(int amount) {
        if (this.quantity < amount) {
            throw new DomainException("Not enough stock for product " + productId);
        }
        this.quantity -= amount;
    }
}
