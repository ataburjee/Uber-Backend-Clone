package com.uber.service;

import com.uber.model.*;
import com.uber.model.dto.RideHistoryResponse;
import com.uber.model.dto.RideRequest;
import com.uber.model.dto.RideResponse;
import com.uber.repository.RideRepository;
import com.uber.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RideService {

    @Autowired
    private RideRepository rideRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    DriverSearchService driverSearchService;

    public ResponseEntity<?> requestRide(RideRequest rideRequest, String riderEmail) {

        User rider = userRepo.findByEmail(riderEmail)
                .orElseThrow(() -> new RuntimeException("Rider not found"));

        //Finding all available drivers from Elasticsearch (within 5 km radius)
        List<DriverES> availableDrivers = driverSearchService.findNearbyDrivers(
                rideRequest.getPickupLat(), rideRequest.getPickupLng(), 5.0);

        if (availableDrivers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No drivers available nearby");
        }

        //Sorting drivers by distance
        availableDrivers.sort(Comparator.comparingDouble(driver ->
                calculateDistance(
                        rideRequest.getPickupLat(), rideRequest.getPickupLng(),
                        driver.getLatitude(), driver.getLongitude()
                )
        ));

        DriverES nearestDriverIndex = availableDrivers.getFirst();

        //Get the User(Driver) Object
        User nearestDriver = userRepo.findById(nearestDriverIndex.getDriverId())
                .orElseThrow(() -> new RuntimeException("Driver not found...!"));

        //Creating and saving the ride
        Ride ride = new Ride();
        ride.setPickupLat(rideRequest.getPickupLat());
        ride.setPickupLng(rideRequest.getPickupLng());
        ride.setDropLat(rideRequest.getDropLat());
        ride.setDropLng(rideRequest.getDropLng());
        ride.setRider(rider);
        ride.setDriver(nearestDriver);
        ride.setStatus(RideStatus.REQUESTED);

        try {
            rideRepo.save(ride);
            return ResponseEntity.ok(
                    new RideResponse(
                            ride.getId(),
                            "Ride requested, finding the nearest driver...",
                            nearestDriver.getEmail()
                    )
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to save ride: " + e.getMessage());
        }
    }


    //Calculating distance between a driver and a rider assuming the distance a straight line
    public double calculateDistance(double lat1,
                                    double lng1,
                                    double lat2,
                                    double lng2)
    {
        return Math.sqrt(Math.pow((lat2-lat1), 2) + (Math.pow((lng2-lng1), 2)));
    }

    public String respondToRide(String rideId, boolean accepted, String driverEmail) {
        Ride ride = rideRepo.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        if (!ride.getDriver().getEmail().equals(driverEmail)) {
            throw new RuntimeException("Not Authorized");
        }

        if (accepted) {
            ride.setStatus(RideStatus.ACCEPTED);
            ride.getDriver().setAvailable(false);
            userRepo.save(ride.getDriver());
            rideRepo.save(ride);
            return "Ride accepted. Proceed to pickup.";
        } else {
            ride.setStatus(RideStatus.REJECTED);
            rideRepo.save(ride);
            return "Ride rejected.";
        }
    }

    public ResponseEntity<?> updateRideStatus(String rideId, RideStatus newStatus, String driverEmail) {
        Ride ride = rideRepo.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        DriverES driverES = driverSearchService.findDriverById(rideId)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        User driver = ride.getDriver();

        if (!driver.getEmail().equals(driverEmail)) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Unauthorized.");
        }

        if (newStatus == RideStatus.IN_PROGRESS && ride.getStatus() != RideStatus.ACCEPTED) {
            return ResponseEntity.badRequest().body("Ride must be ACCEPTED to start.");
        }

        if (newStatus == RideStatus.COMPLETED && ride.getStatus() != RideStatus.IN_PROGRESS) {
            return ResponseEntity.badRequest().body("Ride must be IN_PROGRESS to complete.");
        }

        if (newStatus == RideStatus.IN_PROGRESS) {
            driverES.setAvailable(false);
            driverSearchService.saveDriver(driverES);
        }

        ride.setStatus(newStatus);

        //When ride is completed
        if (newStatus == RideStatus.COMPLETED) {
            driver.setAvailable(true);
            userRepo.save(driver);
            driverES.setAvailable(true);
            driverSearchService.saveDriver(driverES);
        }
        rideRepo.save(ride);

        return ResponseEntity.ok("Ride status updated to " + newStatus);
    }

    public List<RideHistoryResponse> getRideHistory(Authentication auth) {
        String email = auth.getName();
        List<RideHistoryResponse> response;
        List<String> roles = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        if (roles.contains(Role.ADMIN.name())) {
            List<Ride> ridesOfADriver = rideRepo.findAll();
            response = ridesOfADriver.stream().map(ride ->
                    getFormattedRideHistory(ride)
                            .setDriverId(ride.getDriver().getId())
                            .setRiderId(ride.getRider().getId()))
                    .toList();
        } else if (roles.contains(Role.DRIVER.name())) {
            List<Ride> ridesOfADriver = rideRepo.findByDriverEmailOrderByRequestedAtDesc(email);
            response = ridesOfADriver.stream().map(ride ->
                    getFormattedRideHistory(ride)
                            .setDriverId(ride.getDriver().getId()))
                    .toList();
        } else if (roles.contains(Role.RIDER.name())) {
            List<Ride> ridesOfARider = rideRepo.findByRiderEmailOrderByRequestedAtDesc(email);
            response = ridesOfARider.stream().map(ride ->
                    getFormattedRideHistory(ride)
                            .setRiderId(ride.getRider().getId()))
                    .toList();
        } else {
            throw new RuntimeException("Not Authorized...");
        }
        return response;
    }

    private RideHistoryResponse getFormattedRideHistory(Ride ride) {
        return new RideHistoryResponse()
                .setRideId(ride.getId())
                .setRequestedAt(ride.getRequestedAt())
                .setPickedupAt(ride.getBoardedAt())
                .setDroppedAt(ride.getDroppedAt());
//             TODO: Pickup location, Drop location
    }

}

