package com.architecture.layered.application.service;

import com.architecture.layered.application.dto.UserCreateRequest;
import com.architecture.layered.domain.model.User;
import com.architecture.layered.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @Transactional
    public User createUser(UserCreateRequest request) {
        User user = new User(request.name());
        return userRepository.save(user);
    }
}
