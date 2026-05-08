package com.acm.seguridad.seguridad_avanzada_acm.repositories;

import com.acm.seguridad.seguridad_avanzada_acm.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
