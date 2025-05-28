package com.uber.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    public ResponseEntity<?> calculateFare(Double distance) {
        double farePerKM = 40;
        double totalFare = distance*farePerKM;
        return ResponseEntity.ok(totalFare);
    }
}
