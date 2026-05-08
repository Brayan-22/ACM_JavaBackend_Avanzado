package com.architecture.hexagonal.infrastructure.config;

import com.architecture.hexagonal.application.port.out.InventoryRepositoryPort;
import com.architecture.hexagonal.application.port.out.OrderRepositoryPort;
import com.architecture.hexagonal.application.port.out.ProductRepositoryPort;
import com.architecture.hexagonal.application.port.out.UserRepositoryPort;
import com.architecture.hexagonal.application.service.OrderManagementService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {

    @Bean
    public OrderManagementService orderManagementService(
            OrderRepositoryPort orderRepository,
            ProductRepositoryPort productRepository,
            InventoryRepositoryPort inventoryRepository,
            UserRepositoryPort userRepository) {
        
        return new OrderManagementService(
                orderRepository, 
                productRepository, 
                inventoryRepository, 
                userRepository
        );
    }
}
