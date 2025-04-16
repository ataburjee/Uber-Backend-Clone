package com.uber.service;

import com.uber.model.User;
import com.uber.model.dto.LoginRequest;
import com.uber.repository.UserRepository;
import com.uber.security.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service
public class AuthService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    JWTService jwtService;

    public ResponseEntity<?> registerUser(User user) throws Exception {

        String username = user.getEmail();
        if (userRepo.existsByEmail(username)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            user.setId(Utility.generateId());
            userRepo.save(user);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public ResponseEntity<?> login(LoginRequest request) {
        Optional<User> optionalUser = userRepo.findByEmail(request.getEmail());
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not registered");
        }

        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getEmail(), request.getPassword()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect username/password");
        }

        String token = jwtService.generateToken(request.getEmail());

        User user = optionalUser.get();
        user.setAvailable(true);
        userRepo.save(user);

        Map<String, String> response = new HashMap<>();
        response.put("id", user.getId());
        response.put("username", user.getEmail());
        response.put("token", token);

        return ResponseEntity.ok(response);
    }

}
