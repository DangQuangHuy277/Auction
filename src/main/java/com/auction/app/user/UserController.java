package com.auction.app.user;

import com.auction.app.user.UserService;
import com.auction.app.user.dto.RegisterRequest;
import com.auction.app.user.entity.User;
import jdk.jfr.Registered;
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
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest userInfo){
        service.register(userInfo);
        return new ResponseEntity<>(Map.of("message", "Account registered successfully!"), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllUsers(){
        List<User> users = service.getAllUser();
        return ResponseEntity.ok(users);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<?> setEnableUser(@PathVariable("userId") Long userId, @RequestBody boolean enabled){
        User user = service.setEnableUser(userId, enabled);
        return ResponseEntity.ok(user);
    }
}
