package com.auction.app.user;

import com.auction.app.user.dto.RegisterRequest;
import com.auction.app.user.entity.Admin;
import com.auction.app.user.entity.Bidder;
import com.auction.app.user.entity.Seller;
import com.auction.app.user.entity.User;
import com.auction.app.utils.exception.ResourceAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

        User user = null;
        if (role.equals("BIDDER")) {
            user = new Bidder(request.getUsername(), request.getEmail(), request.getPhone(), request.getPassword());
        } else if (role.equals("SELLER")) {
            user = new Seller(request.getUsername(), request.getEmail(), request.getPhone(), request.getPassword());
        } else if (role.equals("ADMIN")) {
            user = new Admin(request.getUsername(), request.getEmail(), request.getPhone(), request.getPassword());
        }
        assert user != null;
        userRepository.save(user);
    }
}
