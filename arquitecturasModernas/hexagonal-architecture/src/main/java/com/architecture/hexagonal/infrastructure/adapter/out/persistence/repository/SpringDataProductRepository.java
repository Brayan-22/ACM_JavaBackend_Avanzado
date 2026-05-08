package com.architecture.hexagonal.infrastructure.adapter.out.persistence.repository;

import com.architecture.hexagonal.infrastructure.adapter.out.persistence.entity.ProductJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataProductRepository extends JpaRepository<ProductJpaEntity, Long> {}
