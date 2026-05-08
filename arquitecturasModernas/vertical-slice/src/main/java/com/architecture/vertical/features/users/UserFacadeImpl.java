package com.architecture.vertical.features.users;


import org.springframework.stereotype.Service;

@Service
class UserFacadeImpl implements UserFacade {
    private final UserRepository repository;

    UserFacadeImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean userExists(Long userId) {
        return repository.existsById(userId);
    }
}
