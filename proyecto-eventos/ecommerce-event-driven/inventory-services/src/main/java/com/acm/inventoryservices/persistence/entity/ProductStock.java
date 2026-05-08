package com.acm.inventoryservices.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_stock")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductStock {
    @Id
    private String productId;
    private Integer availableQuantity;

    public void decreaseQuantity(int amount) {
        if (this.availableQuantity < amount) {
            throw new IllegalArgumentException("Not enough stock");
        }
        this.availableQuantity -= amount;
    }

    public void increaseQuantity(int amount) {
        this.availableQuantity += amount;
    }
}
