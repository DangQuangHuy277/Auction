package com.auction.app.user.controller;


import com.auction.app.user.service.UserService;
import com.auction.app.user.dto.LoginRequest;
import com.auction.app.user.dto.RegisterRequest;
import com.auction.app.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest userInfo) {
        service.register(userInfo);
        return new ResponseEntity<>(Map.of("message", "Account registered successfully!"), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        User user = service.login(loginRequest);
        if (user != null) return ResponseEntity.ok(user);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong password");
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = service.getAllUser();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable("userId") Long userId){
        User user = service.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<?> setEnableUser(@PathVariable("userId") Long userId, @RequestBody boolean enabled) {
        User user = service.setEnableUser(userId, enabled);
        return ResponseEntity.ok(user);
    }
}
