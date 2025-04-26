package com.uber.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RideResponse {
    private String rideId;
    private String message;
    private String driverEmail;
}

