package com.architecture.hexagonal.domain.model;

import com.architecture.hexagonal.domain.exception.DomainException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void addItem_throwsException_whenProductIsInactive() {
        // Arrange
        Order order = new Order(1L, 99L);
        Product inactiveProduct = new Product(10L, "TV", 500.0, false);

        // Act & Assert
        assertThrows(DomainException.class, () -> order.addItem(inactiveProduct, 1));
    }

    @Test
    void addItem_addsCorrectly_andRecalculatesTotal() {
        // Arrange
        Order order = new Order(1L, 99L);
        Product laptop = new Product(1L, "Laptop", 1000.0, true);
        Product mouse = new Product(2L, "Mouse", 20.0, true);

        // Act
        order.addItem(laptop, 1);
        order.addItem(mouse, 2);

        // Assert
        assertEquals(1040.0, order.getTotalAmount(), 0.001);
        assertEquals(2, order.getItems().size());
    }

    @Test
    void cancel_throwsException_whenOrderIsPaid() {
        // Arrange
        Order order = new Order(1L, 99L, OrderStatus.PAID, 100.0, null);

        // Act & Assert
        assertThrows(DomainException.class, order::cancel);
    }
}
