package com.architecture.vertical.features.orders;

import org.springframework.data.jpa.repository.JpaRepository;
interface OrderRepository extends JpaRepository<Order, Long> {}
