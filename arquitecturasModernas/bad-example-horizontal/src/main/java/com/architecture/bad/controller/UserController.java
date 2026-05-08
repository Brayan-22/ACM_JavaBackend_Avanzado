package com.architecture.bad.controller;

import com.architecture.bad.entity.User;
import com.architecture.bad.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bad/user")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    @PostMapping
    public User create(@RequestBody User user){
        return userRepository.save(user);
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id){
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @GetMapping
    public List<User> getAll(){
        return userRepository.findAll();
    }
}
