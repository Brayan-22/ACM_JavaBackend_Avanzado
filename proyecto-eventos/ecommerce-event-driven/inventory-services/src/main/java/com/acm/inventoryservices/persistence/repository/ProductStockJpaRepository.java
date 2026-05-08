package com.acm.inventoryservices.persistence.repository;

import com.acm.inventoryservices.persistence.entity.ProductStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductStockJpaRepository extends JpaRepository<ProductStock, String> {
}
