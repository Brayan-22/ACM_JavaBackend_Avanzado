package com.architecture.hexagonal.domain.model;

import lombok.Getter;

@Getter
public class Product {
    private final Long id;
    private final String name;
    private final boolean active;
    private final double price;

    public Product(Long id, String name, double price, boolean active) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.active = active;
    }
}
