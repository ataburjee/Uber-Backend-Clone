package com.uber.model.dto;

import com.uber.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginResponse {
    private String id;
    private String username;
    private Role role;
    private String token;
}
