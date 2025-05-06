package com.uber.service;

import com.uber.model.Ride;
import com.uber.model.RideStatus;
import com.uber.model.Role;
import com.uber.model.User;
import com.uber.model.dto.AdminDashboardStats;
import com.uber.repository.RideRepository;
import com.uber.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final RideRepository rideRepository;

    @Autowired
    public AdminService(UserRepository userRepository, RideRepository rideRepository) {
        this.userRepository = userRepository;
        this.rideRepository = rideRepository;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public List<Ride> findAllRides() {
        return rideRepository.findAll();
    }

    public ResponseEntity<?> deleteUserById(String id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    public ResponseEntity<AdminDashboardStats> getStats() {
        try {
            long totalUsers = userRepository.count();
            long totalRiders = userRepository.countByRole(Role.RIDER);
            long totalDrivers = userRepository.countByRole(Role.DRIVER);

            long activeRides = rideRepository.countByStatus(RideStatus.IN_PROGRESS);
            long completedRides = rideRepository.countByStatus(RideStatus.COMPLETED);
            long pendingRequests = rideRepository.countByStatus(RideStatus.REQUESTED);

            AdminDashboardStats stats = new AdminDashboardStats(
                    totalUsers,
                    totalRiders,
                    totalDrivers,
                    activeRides,
                    completedRides,
                    pendingRequests
            );
            return ResponseEntity.ok(stats);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
