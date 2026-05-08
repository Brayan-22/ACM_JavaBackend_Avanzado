package acm.javaspring.persistenciaavanzada.service;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IBaseService<T> {
    Optional<T> findById(Long id);
    List<T> findAll(Pageable pageable);
    T save(T entity);
    void deleteById(Long id);
    Optional<T> update(Long id, T entity);
}
