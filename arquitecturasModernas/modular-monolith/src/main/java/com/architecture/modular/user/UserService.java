package com.architecture.modular.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(repository.findByUsername(username));
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User save(User user) {
        return repository.save(user);
    }

    public Optional<User> update(Long id, User user) {
        return repository.findById(id)
                .map(existingUser -> {
                    existingUser.setUsername(user.getUsername());
                    existingUser.setPassword(user.getPassword());
                    return repository.save(existingUser);
                });
    }

    public boolean delete(String username){
        var user = repository.findByUsername(username);
        if (user == null) {
            return false;
        }
        repository.delete(user);
        return true;
    }
}
