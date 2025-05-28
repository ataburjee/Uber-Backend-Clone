package com.uber.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uber.model.dto.RideRequest;
import com.uber.service.RideService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(RideController.class)
@AutoConfigureMockMvc(addFilters = false) // Skip filters like JWT for isolated controller testing
class RideControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RideService rideService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testRequestRide() throws Exception {
        RideRequest request = new RideRequest(22, 33, 44, 55);

        mockMvc.perform(post("/api/rides/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());        
    }

@Test
    void respondToRide() {
    }

    @Test
    void updateRideStatus() {
    }

    @Test
    void getRideHistory() {
    }
}