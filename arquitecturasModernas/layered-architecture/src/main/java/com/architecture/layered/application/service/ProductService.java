package com.architecture.layered.application.service;

import com.architecture.layered.application.dto.ProductCreateRequest;
import com.architecture.layered.domain.model.Product;
import com.architecture.layered.infrastructure.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    @Transactional
    public Product createProduct(ProductCreateRequest request) {
        Product product = new Product(request.name(), request.price());
        return productRepository.save(product);
    }
}
