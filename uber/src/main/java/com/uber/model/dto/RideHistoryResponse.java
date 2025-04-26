package com.uber.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class RideHistoryResponse {

    private String rideId;
    private String driverId;
    private String riderId;
    private LocalDateTime requestedAt;
    private LocalDateTime pickedupAt;
    private LocalDateTime droppedAt;

    public RideHistoryResponse setRideId(String rideId){
        this.rideId = rideId;
        return this;
    }

    public RideHistoryResponse setRiderId(String riderId){
        this.riderId = riderId;
        return this;
    }

    public RideHistoryResponse setDriverId(String driverId){
        this.driverId = driverId;
        return this;
    }

    public RideHistoryResponse setRequestedAt(LocalDateTime requestedAt){
        this.requestedAt = requestedAt;
        return this;
    }

    public RideHistoryResponse setPickedupAt(LocalDateTime pickedupAt){
        this.pickedupAt = pickedupAt;
        return this;
    }

    public RideHistoryResponse setDroppedAt(LocalDateTime droppedAt){
        this.droppedAt = droppedAt;
        return this;
    }

}
