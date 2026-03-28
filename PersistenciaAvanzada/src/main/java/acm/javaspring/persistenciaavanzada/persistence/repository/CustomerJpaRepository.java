package acm.javaspring.persistenciaavanzada.persistence.repository;

import acm.javaspring.persistenciaavanzada.persistence.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerJpaRepository extends JpaRepository<CustomerEntity,Long> {
}
