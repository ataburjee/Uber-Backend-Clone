package com.uber.service;

import java.util.UUID;

public class Utility {

    public static String generateId() {
        return "UBER-" + UUID.randomUUID()
                .toString()
                .replaceAll("-", "")
                .substring(0, 6)
                .toUpperCase()
                + "-" + System.currentTimeMillis();
    }

}

