package com.acm.seguridad.seguridad_avanzada_acm.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.Objects;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product name cannot be empty")
    @Column(nullable = false)
    private String name;

    @Min(value = 0, message = "Price cannot be negative")
    private double price;

    @Column(name = "owner_username")
    private String ownerUsername;

}
