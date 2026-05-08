package com.architecture.hexagonal.application.port.out;

import com.architecture.hexagonal.domain.model.Product;
import java.util.Optional;

public interface ProductRepositoryPort {
    Optional<Product> findById(Long id);
}
