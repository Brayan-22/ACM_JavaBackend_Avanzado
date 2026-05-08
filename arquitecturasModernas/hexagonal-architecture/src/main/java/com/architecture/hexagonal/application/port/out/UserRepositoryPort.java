package com.architecture.hexagonal.application.port.out;

import com.architecture.hexagonal.domain.model.User;
import java.util.Optional;

public interface UserRepositoryPort {
    Optional<User> findById(Long id);
}
