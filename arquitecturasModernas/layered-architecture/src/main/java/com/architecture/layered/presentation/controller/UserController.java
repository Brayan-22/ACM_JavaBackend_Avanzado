package com.architecture.layered.presentation.controller;

import com.architecture.layered.application.dto.UserCreateRequest;
import com.architecture.layered.application.service.UserService;
import com.architecture.layered.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/layered/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody UserCreateRequest request) {
        return userService.createUser(request);
    }
}
