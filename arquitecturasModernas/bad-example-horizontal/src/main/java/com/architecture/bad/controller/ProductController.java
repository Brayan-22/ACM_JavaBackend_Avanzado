package com.architecture.bad.controller;

import com.architecture.bad.entity.Inventory;
import com.architecture.bad.entity.Product;
import com.architecture.bad.repository.InventoryRepository;
import com.architecture.bad.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bad/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @PostMapping
    public Product createProduct(@RequestBody Product productRequest) {
        // Antipatrón: Entidad de base de datos como payload de entrada de web
        Product saved = productRepository.save(productRequest);
        
        // Efecto secundario implícito: inicializar inventario en 0. 
        // ¡Mezclado sin transaccionalidad explícita o servicio!
        Inventory inv = new Inventory();
        inv.setProduct(saved);
        inv.setQuantity(0);
        inventoryRepository.save(inv);
        
        return saved;
    }
}
