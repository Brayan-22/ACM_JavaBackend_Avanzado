package com.architecture.vertical.features.products;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

interface ProductRepository extends JpaRepository<Product, Long> {
}
