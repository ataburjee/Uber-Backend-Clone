package com.uber.service;

import com.uber.model.DriverES;
import com.uber.model.Role;
import com.uber.model.User;
import com.uber.model.dto.LoginRequest;
import com.uber.model.dto.LoginResponse;
import com.uber.repository.DriverESRepository;
import com.uber.repository.UserRepository;
import com.uber.security.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AuthService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private DriverESRepository esRepository;

    public ResponseEntity<?> registerUser(User user) throws Exception {

        String username = user.getEmail();
        if (userRepo.existsByEmail(username)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists...!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            String id = Utility.generateId();
            user.setId(id);
            userRepo.save(user);

            if (user.getRole() == Role.DRIVER) {
                DriverES driverES = new DriverES();
                driverES.setId(Utility.generateId());
                driverES.setDriverId(id);
                driverES.setEmail(user.getEmail());
                driverES.setAvailable(false);

                esRepository.save(driverES);
            }
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public ResponseEntity<?> login(LoginRequest request) {
        Optional<User> optionalUser = userRepo.findByEmail(request.getEmail());
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not registered...!");
        }

        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect username/password");
        }

        String token = jwtService.generateToken(request.getEmail());

        User user = optionalUser.get();
        user.setAvailable(true);
        userRepo.save(user);

        if (user.getRole() == Role.DRIVER) {
            DriverES driver = esRepository.findByDriverId(user.getId())
                    .orElseThrow(() -> new RuntimeException("No driver available...!"));

            driver.setAvailable(true);
            esRepository.save(driver);
        }

        return ResponseEntity.ok(
                new LoginResponse(user.getId(), user.getEmail(), user.getRole(), token)
        );

    }

}
