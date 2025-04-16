package com.uber.model.dto;

import lombok.Data;

@Data
public class RideRequest {
    private double pickupLat;
    private double pickupLng;
    private double dropLat;
    private double dropLng;
}

