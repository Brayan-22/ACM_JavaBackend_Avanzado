package com.architecture.vertical.features.products;

public interface ProductFacade {
    boolean isProductActiveAndValid(Long productId);
    double getProductPrice(Long productId);
}
