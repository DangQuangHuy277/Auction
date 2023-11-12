package com.auction.app.user.service;

import com.auction.app.user.dto.LoginRequest;
import com.auction.app.user.dto.RegisterRequest;
import com.auction.app.user.entity.Admin;
import com.auction.app.user.entity.Bidder;
import com.auction.app.user.entity.Seller;
import com.auction.app.user.entity.User;
import com.auction.app.user.repository.UserRepository;
import com.auction.app.exception.ResourceAlreadyExistException;
import com.auction.app.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail()))
            throw new ResourceAlreadyExistException("Email is already in use!");
        if (userRepository.existsByUsername(request.getUsername()))
            throw new ResourceAlreadyExistException("Username is already in use!");
        String role = request.getRole();

        User user = new Bidder();
        if (role.equals("BIDDER")) {
            user = new Bidder(request.getUsername(), request.getEmail(), request.getPhone(), passwordEncoder.encode(request.getPassword()), true);
        } else if (role.equals("SELLER")) {
            user = new Seller(request.getUsername(), request.getEmail(), request.getPhone(), passwordEncoder.encode(request.getPassword()), true);
        } else if (role.equals("ADMIN")) {
            user = new Admin(request.getUsername(), request.getEmail(), request.getPhone(), passwordEncoder.encode(request.getPassword()), true);
        }
        userRepository.save(user);
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public User setEnableUser(Long userId, boolean enabled) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("The user is not found"));
        user.setEnabled(enabled);
        return userRepository.save(user);
    }

    public User login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("The user is not found"));
        if(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) return user;
        else return null;
    }
}
