package com.uber.controller;

import com.uber.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/calculate")
    public ResponseEntity<?> getFare(@RequestParam("distance") Double distance) {
        return paymentService.calculateFare(distance);
    }

    //Payment related methods

}
