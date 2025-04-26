package com.uber.controller;

import com.uber.model.User;
import com.uber.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/uber/driver")
public class DriverController {

    @Autowired
    private UserRepository userRepo;

    @PutMapping("/location")
    @PreAuthorize("hasAuthority('DRIVER')")
    public ResponseEntity<String> updateLocation(@RequestParam double lat, @RequestParam double lng, Authentication auth) {

        User driver = userRepo.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        driver.setCurrentLat(lat);
        driver.setCurrentLng(lng);
        driver.setAvailable(true); //Whenever updates the location that means the driver is online & available
        userRepo.save(driver);

        return ResponseEntity.ok("Location updated. You're now available.");
    }
}

