package com.uber.model;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "drivers")
@Data
public class DriverES {

    @Id
    private String id;

    private String driverId;
    private String email;
    private double latitude;
    private double longitude;
    private boolean available;
}

