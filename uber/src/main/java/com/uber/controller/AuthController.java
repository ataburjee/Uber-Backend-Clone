package com.uber.controller;

import com.uber.model.User;
import com.uber.model.dto.LoginRequest;
import com.uber.repository.DriverESRepository;
import com.uber.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private DriverESRepository esRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User request) throws Exception {
        return authService.registerUser(request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}

