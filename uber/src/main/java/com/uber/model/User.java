package com.uber.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Entity
@Data
public class User {

    @Id
    private String id;

    @Email(message = "Please provide a valid email...!")
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Double currentLat;

    private Double currentLng;

    private boolean isAvailable;

}
