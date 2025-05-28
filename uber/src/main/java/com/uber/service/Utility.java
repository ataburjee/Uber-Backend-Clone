package com.uber.service;

import java.util.UUID;
import static java.lang.Math.*;

public class Utility {

    public static final double R = 6371;

    public static String generateId() {
        return "UBER-" + UUID.randomUUID()
                .toString()
                .replaceAll("-", "")
                .substring(0, 6)
                .toUpperCase()
                + "-" + System.currentTimeMillis();
    }

    public static double calculateDistance(double lat1,
                                           double lng1,
                                           double lat2,
                                           double lng2)
    {
        double Δϕ = toRadians(lat2 - lat1);
        double Δλ = toRadians(lng2 - lng1);
        double ϕ1 = toRadians(lat1);
        double ϕ2 = toRadians(lat2);

        double a = pow(sin(Δϕ/2), 2) + cos(ϕ1)*cos(ϕ2)*pow(sin(Δλ/2), 2);
        double c = 2*atan2(sqrt(a), sqrt(1-a));

        return R*c;

    }

}

