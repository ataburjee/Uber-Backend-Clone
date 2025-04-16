package com.uber.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RideResponse {
    private String rideId;
    private String message;
    private String driverEmail;
}

