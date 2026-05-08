package com.architecture.layered.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ORDERS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // Obliga a usar constructor semántico
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private double totalAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    public Order(User user) {
        this.user = user;
        this.status = OrderStatus.CREATED;
        this.totalAmount = 0.0;
    }

    public void addItem(Product product, int quantity) {
        if (!product.isActive()) {
            throw new IllegalStateException("Product is inactive");
        }
        OrderItem item = new OrderItem(this, product, quantity, product.getPrice());
        this.items.add(item);
        this.calculateTotal();
    }

    private void calculateTotal() {
        this.totalAmount = this.items.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }

    public void cancel() {
        if (this.status == OrderStatus.PAID) {
            throw new IllegalStateException("Cannot cancel a paid order.");
        }
        this.status = OrderStatus.CANCELLED;
    }
}
