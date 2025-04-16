package com.uber.controller;

import com.uber.model.RideStatus;
import com.uber.model.dto.RideHistoryResponse;
import com.uber.model.dto.RideRequest;
import com.uber.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/uber/ride")
public class RideController {

    @Autowired
    private RideService rideService;

    @PostMapping("/request")
    @PreAuthorize("hasRole('RIDER')")
    public ResponseEntity<?> requestRide(@RequestBody RideRequest request, Authentication auth) {
        return rideService.requestRide(request, auth.getName());
    }

    @PostMapping("/respond/{id}")
    @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<String> respondToRide(@PathVariable("id") String rideId,
                                                @RequestParam boolean accept,
                                                Authentication auth) {
        String driverEmail = auth.getName();
        String message = rideService.respondToRide(rideId, accept, driverEmail);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/update-status")
    public ResponseEntity<?> updateRideStatus(
            @RequestParam("id") String rideId,
            @RequestParam RideStatus status,
            @RequestParam String driverEmail) {

        return rideService.updateRideStatus(rideId, status, driverEmail);
    }

    @GetMapping("/ride-history")
    public ResponseEntity<?> getRideHistory(Authentication auth) {
        List<RideHistoryResponse> rideHistory = rideService.getRideHistory(auth);
        if (rideHistory.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Rides Found...!");
        return ResponseEntity.ok(rideHistory);
    }

}
