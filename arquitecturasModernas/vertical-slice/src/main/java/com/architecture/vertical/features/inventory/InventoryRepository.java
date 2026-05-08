package com.architecture.vertical.features.inventory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// --- Repository ---
interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByProductId(Long productId);
}

