package com.architecture.hexagonal.application.service;

import com.architecture.hexagonal.application.port.in.CancelOrderUseCase;
import com.architecture.hexagonal.application.port.in.CreateOrderUseCase;
import com.architecture.hexagonal.application.port.out.InventoryRepositoryPort;
import com.architecture.hexagonal.application.port.out.OrderRepositoryPort;
import com.architecture.hexagonal.application.port.out.ProductRepositoryPort;
import com.architecture.hexagonal.application.port.out.UserRepositoryPort;
import com.architecture.hexagonal.domain.exception.DomainException;
import com.architecture.hexagonal.domain.model.Inventory;
import com.architecture.hexagonal.domain.model.Order;
import com.architecture.hexagonal.domain.model.Product;

public class OrderManagementService implements CreateOrderUseCase, CancelOrderUseCase {

    private final OrderRepositoryPort orderRepository;
    private final ProductRepositoryPort productRepository;
    private final InventoryRepositoryPort inventoryRepository;
    private final UserRepositoryPort userRepository;

    public OrderManagementService(OrderRepositoryPort orderRepository,
                                  ProductRepositoryPort productRepository,
                                  InventoryRepositoryPort inventoryRepository,
                                  UserRepositoryPort userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public OrderDto createOrder(OrderCommand command) {
        userRepository.findById(command.userId())
                .orElseThrow(() -> new DomainException("User not found"));

        Order order = new Order(null, command.userId());

        for (ItemCommand itemCmd : command.items()) {
            Product product = productRepository.findById(itemCmd.productId())
                    .orElseThrow(() -> new DomainException("Product not found"));

            Inventory inventory = inventoryRepository.findByProductId(product.getId())
                    .orElseThrow(() -> new DomainException("Inventory not found"));

            inventory.decrease(itemCmd.quantity());
            inventoryRepository.save(inventory);

            order.addItem(product, itemCmd.quantity());
        }

        Order savedOrder = orderRepository.save(order);
        return new OrderDto(savedOrder.getId(), savedOrder.getStatus().name(), savedOrder.getTotalAmount());
    }

    @Override
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new DomainException("Order not found"));
        
        order.cancel();
        orderRepository.save(order);
    }
}
