package com.acm.paymentservice.persistence.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String orderId;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    private Double amount;

    private LocalDateTime createdAt;
    private String transactionId;
}