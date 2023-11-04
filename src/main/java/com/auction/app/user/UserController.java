package com.auction.app.user;

import com.auction.app.user.dto.RegisterRequest;
import jdk.jfr.Registered;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.Map;

@RestController
@RequestMapping("/v1/api/users")
@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest userInfo){
        service.register(userInfo);
        return new ResponseEntity<>(Map.of("message", "Account registered successfully!"), HttpStatus.CREATED);
    }
}
