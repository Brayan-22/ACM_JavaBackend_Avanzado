package acm.javaspring.persistenciaavanzada.persistence.repository;

import acm.javaspring.persistenciaavanzada.persistence.entity.PurchaseOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<PurchaseOrderEntity,Long> {
}
