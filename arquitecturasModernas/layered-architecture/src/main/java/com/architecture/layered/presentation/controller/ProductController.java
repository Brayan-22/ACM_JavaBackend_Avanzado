package com.architecture.layered.presentation.controller;

import com.architecture.layered.application.dto.ProductCreateRequest;
import com.architecture.layered.application.service.ProductService;
import com.architecture.layered.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/layered/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody ProductCreateRequest request) {
        return productService.createProduct(request);
    }
}
