package com.architecture.vertical.features.products;

import org.springframework.stereotype.Service;

@Service
class ProductFacadeImpl implements ProductFacade {

    private final ProductRepository repository;

    ProductFacadeImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean isProductActiveAndValid(Long productId) {
        return repository.findById(productId)
                .map(Product::isActive)
                .orElse(false);
    }

    @Override
    public double getProductPrice(Long productId) {
        return repository.findById(productId)
                .map(Product::getPrice)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }
}
