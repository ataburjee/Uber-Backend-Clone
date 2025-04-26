package com.uber.controller;

import com.uber.model.DriverES;
import com.uber.model.dto.LoginRequest;
import com.uber.model.User;
import com.uber.repository.DriverESRepository;
import com.uber.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;
import java.util.UUID;

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

