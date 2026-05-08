package com.acm.paymentservice.persistence.repository;

import com.acm.paymentservice.persistence.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentJpaRepository extends JpaRepository<Payment, String> {
    boolean existsByOrderId(String orderId);
}
