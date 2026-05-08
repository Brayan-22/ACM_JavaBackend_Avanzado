package com.architecture.bad.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    // Antipatrón: Usar un string o boolean en vez de un Enum o estado con comportamiento, expuesto con setters públicos.
    private boolean active;
    
    private double defaultPrice;
}
