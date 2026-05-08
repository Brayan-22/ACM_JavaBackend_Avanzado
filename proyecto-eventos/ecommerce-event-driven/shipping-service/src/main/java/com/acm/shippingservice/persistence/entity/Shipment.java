package com.acm.shippingservice.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "shipments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String orderId;
    private String trackingNumber;
    @Enumerated(EnumType.STRING)
    private ShipmentStatus status;
    private LocalDateTime scheduledAt;
}