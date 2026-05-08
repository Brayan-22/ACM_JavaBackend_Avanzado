package com.architecture.vertical.features.orders;

import jakarta.persistence.* ;
import lombok.Data;

@Entity
@Table(name = "ORDER_ITEMS")
@Data
class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private int quantity;
    private double price;
}
