package com.architecture.hexagonal.infrastructure.adapter.out.persistence.repository;

import com.architecture.hexagonal.infrastructure.adapter.out.persistence.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataUserRepository extends JpaRepository<UserJpaEntity, Long> {}

