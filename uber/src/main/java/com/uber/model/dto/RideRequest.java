package com.uber.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RideRequest {
    private double pickupLat;
    private double pickupLng;
    private double dropLat;
    private double dropLng;
}

