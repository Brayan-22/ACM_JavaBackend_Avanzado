package com.acm.paymentservice.controller;

import com.acm.paymentservice.persistence.repository.PaymentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
public class PaymentController {
    private final PaymentJpaRepository paymentJpaRepository;

    @GetMapping
    public ResponseEntity<?> getAllPayments(){
        return ResponseEntity.ok(paymentJpaRepository.findAll());
    }
}
