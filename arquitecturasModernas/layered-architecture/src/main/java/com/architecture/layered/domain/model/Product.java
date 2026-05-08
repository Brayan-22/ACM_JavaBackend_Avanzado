package com.architecture.layered.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private boolean active;

    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }
    
    public void activate() {
        this.active = true;
    }
}
