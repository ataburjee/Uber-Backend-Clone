package com.uber.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ride {

    @Id
    @Column(nullable = false, unique = true)
    private String id;

    private double pickupLat;
    private double pickupLng;

    private double dropLat;
    private double dropLng;

    @ManyToOne
    private User rider;

    @ManyToOne
    private User driver;

    @Enumerated(EnumType.STRING)
    private RideStatus status;

    @Column(updatable = false)
    private LocalDateTime requestedAt = LocalDateTime.now();

    private LocalDateTime boardedAt;

    private LocalDateTime droppedAt;

}