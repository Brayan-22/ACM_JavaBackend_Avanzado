package com.acm.shippingservice.persistence.repository;

import com.acm.shippingservice.persistence.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentJpaRepository extends JpaRepository<Shipment, String> {
    boolean existsByOrderId(String orderId);
}
