package com.acm.shippingservice.controller;

import com.acm.shippingservice.persistence.repository.ShipmentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shipping")
public class ShippingController {
    private final ShipmentJpaRepository repository;

    @GetMapping
    public ResponseEntity<?> getAllShipments() {
        return ResponseEntity.ok(repository.findAll());
    }
}
