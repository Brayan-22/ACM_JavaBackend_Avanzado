package com.architecture.bad.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Inventory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Antipatrón: Mapeo directo y bidireccional fuerte e innecesario, acoplamiento alto
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;
    
    private int quantity;
}
