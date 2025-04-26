package com.uber.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdminDashboardStats {
    private long totalUsers;
    private long totalRiders;
    private long totalDrivers;
    private long activeRides;
    private long completedRides;
    private long pendingRequests;


}

