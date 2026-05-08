package com.architecture.hexagonal.infrastructure.adapter.out.persistence;

import com.architecture.hexagonal.application.port.out.UserRepositoryPort;
import com.architecture.hexagonal.domain.model.User;
import com.architecture.hexagonal.infrastructure.adapter.out.persistence.repository.SpringDataUserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class UserPersistenceAdapter implements UserRepositoryPort {

    private final SpringDataUserRepository repository;

    public UserPersistenceAdapter(SpringDataUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<User> findById(Long id) {
        return repository.findById(id).map(entity -> 
            new User(entity.getId(), entity.getName())
        );
    }
}
