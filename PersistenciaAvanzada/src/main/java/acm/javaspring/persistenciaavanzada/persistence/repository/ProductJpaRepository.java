package acm.javaspring.persistenciaavanzada.persistence.repository;

import acm.javaspring.persistenciaavanzada.persistence.entity.ProductEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductJpaRepository extends JpaRepository<ProductEntity,Long> {
    List<ProductEntity> findAllByDeletedFalse();
    Optional<ProductEntity> findByIdAndDeletedFalse(Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from ProductEntity p where p.id = :id and p.deleted = false")
    Optional<ProductEntity> findByIdForUpdate(@Param("id") Long id);
}
