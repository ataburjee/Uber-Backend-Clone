package com.uber.service;

import com.uber.model.DriverES;
import com.uber.model.User;
import com.uber.repository.DriverESRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DriverSearchService {

    @Autowired
    private DriverESRepository driverESRepository;

    public List<DriverES> findNearbyDrivers(double pickupLat, double pickupLng, double radiusKm) {

        List<DriverES> availableDrivers = driverESRepository.findByAvailableTrue();

        List<DriverES> list = availableDrivers.stream()
                .filter(driver -> {
                    double distance = calculateDistance(
                            pickupLat, pickupLng,
                            driver.getLatitude(), driver.getLongitude()
                    );
                    return distance <= radiusKm;
                })
                .toList();
        return new ArrayList<>(list);
    }

    //Calculating distance between a driver and a rider assuming the distance a straight line
    public double calculateDistance(double lat1,
                                    double lng1,
                                    double lat2,
                                    double lng2)
    {
        return Math.sqrt(Math.pow((lat2-lat1), 2) + (Math.pow((lng2-lng1), 2)));
    }

    public Optional<DriverES> findDriverById(String driverId) {
        return driverESRepository.findById(driverId);
    }

    public void updateDriverLocationAndAvailability(User driver) {
        DriverES index = new DriverES();
        index.setId(Utility.generateId());
        index.setDriverId(driver.getId());
        index.setEmail(driver.getEmail());
        index.setLatitude(driver.getCurrentLat());
        index.setLongitude(driver.getCurrentLng());
        index.setAvailable(driver.isAvailable());

        driverESRepository.save(index);
    }

    public void saveDriver(DriverES driverES) {
        try {
            driverESRepository.save(driverES);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

