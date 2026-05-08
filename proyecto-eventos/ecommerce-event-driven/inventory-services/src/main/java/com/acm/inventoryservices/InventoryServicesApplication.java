package com.acm.inventoryservices;

import com.acm.inventoryservices.persistence.entity.ProductStock;
import com.acm.inventoryservices.persistence.repository.ProductStockJpaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServicesApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(ProductStockJpaRepository repository){
        return args -> {
            if (repository.count() == 0){
                repository.save(new ProductStock("product-1", 10));
                repository.save(new ProductStock("product-2", 5));
                repository.save(new ProductStock("product-3", 0));
            }
        };
    }
}
