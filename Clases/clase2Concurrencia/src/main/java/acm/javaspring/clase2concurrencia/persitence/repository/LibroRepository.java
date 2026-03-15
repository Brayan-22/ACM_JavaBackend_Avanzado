package acm.javaspring.clase2concurrencia.persitence.repository;

import acm.javaspring.clase2concurrencia.persitence.entity.LibroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository<LibroEntity, Integer> {
}
