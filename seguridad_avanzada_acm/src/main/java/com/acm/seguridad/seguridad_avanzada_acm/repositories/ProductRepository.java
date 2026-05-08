package com.acm.seguridad.seguridad_avanzada_acm.repositories;

import com.acm.seguridad.seguridad_avanzada_acm.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
