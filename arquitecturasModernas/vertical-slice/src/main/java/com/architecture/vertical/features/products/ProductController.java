package com.architecture.vertical.features.products;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/vertical/products")
class ProductController { // Package private

    private final ProductRepository repository;

    ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    List<Product> getAllProducts() {
        return repository.findAll();
    }

    @PostMapping
    Product createProduct(@RequestBody Product product) {
        return repository.save(product);
    }
}
