package com.uber.repository;

import com.uber.model.Ride;
import com.uber.model.RideStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RideRepository extends JpaRepository<Ride, Long> {

    Optional<Ride> findById(String rideId);

    List<Ride> findByRiderEmailOrderByRequestedAtDesc(String riderEmail);

    List<Ride> findByDriverEmailOrderByRequestedAtDesc(String driverEmail);

    long countByStatus(RideStatus status);

    boolean existsByDriverIdAndStatus(String driverId, RideStatus rideStatus);

}

