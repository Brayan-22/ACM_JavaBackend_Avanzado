package com.acm.inventoryservices.persistence.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "inventory_reservation")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InventoryReservation {
    @EmbeddedId
    private ReservationId id;
    private Integer quantity;
}
