package com.uber.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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

