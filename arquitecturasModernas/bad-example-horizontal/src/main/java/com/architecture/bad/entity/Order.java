package com.architecture.bad.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ORDERS")
@Data // Antipatrón: Data genera equals/hashcode circulares con colecciones, setters de todo.
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Status string simple sin reglas.
    private String status;
    
    private double totalAmount;
    
    private Date creationDate;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    // Cascades agresivos exponiendo la base de datos a nivel objeto
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderItem> items = new ArrayList<>();
}
