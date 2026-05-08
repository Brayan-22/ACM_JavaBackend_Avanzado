package com.architecture.hexagonal.infrastructure.adapter.out.persistence.repository;

import com.architecture.hexagonal.infrastructure.adapter.out.persistence.entity.InventoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataInventoryRepository extends JpaRepository<InventoryJpaEntity, Long> {
    Optional<InventoryJpaEntity> findByProductId(Long productId);
}
