package com.architecture.hexagonal.infrastructure.adapter.out.persistence;

import com.architecture.hexagonal.application.port.out.ProductRepositoryPort;
import com.architecture.hexagonal.domain.model.Product;
import com.architecture.hexagonal.infrastructure.adapter.out.persistence.entity.ProductJpaEntity;
import com.architecture.hexagonal.infrastructure.adapter.out.persistence.repository.SpringDataProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;



@Component
public class ProductPersistenceAdapter implements ProductRepositoryPort {

    private final SpringDataProductRepository repository;

    public ProductPersistenceAdapter(SpringDataProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return repository.findById(id).map(entity -> 
            new Product(entity.getId(), entity.getName(), entity.getPrice(), entity.isActive())
        );
    }
}
