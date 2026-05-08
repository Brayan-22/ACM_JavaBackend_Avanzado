package com.acm.inventoryservices.controller;

import com.acm.inventoryservices.persistence.repository.ProductStockJpaRepository;
import com.acm.inventoryservices.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.BASE_API)
@Slf4j
@RequiredArgsConstructor
public class InventoryController {
    private final ProductStockJpaRepository productStockJpaRepository;

    @GetMapping
    public ResponseEntity<?> getAllStock(){
        return ResponseEntity.ok(productStockJpaRepository.findAll());
    }
}
