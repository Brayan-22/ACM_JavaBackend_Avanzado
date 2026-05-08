package com.architecture.hexagonal.infrastructure.adapter.out.persistence.repository;

import com.architecture.hexagonal.infrastructure.adapter.out.persistence.entity.OrderJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataOrderRepository extends JpaRepository<OrderJpaEntity, Long> {}
