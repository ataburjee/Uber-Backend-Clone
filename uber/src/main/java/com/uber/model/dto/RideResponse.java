package com.uber.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RideResponse {
    private String rideId;
    private String riderId;
    private String riderEmail;
    private String driverId;
    private String driverEmail;
    private String message;
}

