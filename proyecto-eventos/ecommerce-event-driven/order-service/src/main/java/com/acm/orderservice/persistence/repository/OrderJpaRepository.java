package com.acm.orderservice.persistence.repository;

import com.acm.orderservice.persistence.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderJpaRepository extends JpaRepository<Order, String> {
}
