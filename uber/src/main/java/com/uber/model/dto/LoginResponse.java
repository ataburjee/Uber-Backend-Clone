package com.uber.model.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoginResponse {
    private String id;
    private String username;
    private String token;
}
