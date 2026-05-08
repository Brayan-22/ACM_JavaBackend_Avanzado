package com.acm.inventoryservices.persistence.repository;

import com.acm.inventoryservices.persistence.entity.InventoryReservation;
import com.acm.inventoryservices.persistence.entity.ReservationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface InventoryReservationJpaRepository extends JpaRepository<InventoryReservation, ReservationId> {
    List<InventoryReservation> findByIdOrderId(String idOrderId);
}
