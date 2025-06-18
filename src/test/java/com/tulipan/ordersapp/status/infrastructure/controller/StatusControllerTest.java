package com.tulipan.ordersapp.status.infrastructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tulipan.ordersapp.status.application.StatusService;
import com.tulipan.ordersapp.status.domain.model.Status;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@Slf4j
@SpringBootTest
@Transactional
class StatusControllerTest {
    Status shippedStatus, deliveredStatus, pendingStatus;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private StatusService statusService;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        Status pending = Status.builder()
            .name("Pending")
            .isActive(true)
            .build();
        Status shipped = Status.builder()
            .name("Shipped")
            .isActive(true)
            .build();
        Status delivered = Status.builder()
            .name("Delivered")
            .isActive(true)
            .build();
        pendingStatus = statusService.save(pending);
        shippedStatus = statusService.save(shipped);
        deliveredStatus = statusService.save(delivered);
    }

    @Test
    void getAllStatuses() throws Exception {
        mockMvc.perform(get("/status"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(3)) // Verifies the number of statuses
            .andExpect(jsonPath("$[0].name").value("Pending")) // Verifies the first status name
            .andExpect(jsonPath("$[0].isActive").value(true)) // Verifies the first status is active
            .andExpect(jsonPath("$[1].name").value("Shipped")) // Verifies the second status name
            .andExpect(jsonPath("$[2].name").value("Delivered")); // Verifies the third status name
    }

    @Test
    void getAllActiveStatuses() throws Exception {
        mockMvc.perform(get("/status/active"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(3)) // Verifies the number of active statuses
            .andExpect(jsonPath("$[0].name").value("Pending")) // Verifies the first active status name
            .andExpect(jsonPath("$[0].isActive").value(true)); // Verifies the first active status is active
    }

    @Test
    void getAllInactiveStatuses() throws Exception {
        Status inactiveStatus = Status.builder()
            .name("Cancelled")
            .isActive(false)
            .build();
        statusService.save(inactiveStatus);

        mockMvc.perform(get("/status/inactive"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1)) // Verifies the number of inactive statuses
            .andExpect(jsonPath("$[0].name").value("Cancelled")) // Verifies the inactive status name
            .andExpect(jsonPath("$[0].isActive").value(false)); // Verifies the inactive status is not active
    }

    @Test
    void getAllInactiveStatusesEmpty() throws Exception {
        mockMvc.perform(get("/status/inactive"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void createStatus() throws Exception {
        Status newStatus = Status.builder()
            .name("Cancelled")
            .isActive(false)
            .build();
        mockMvc.perform(post("/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newStatus)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value(newStatus.getName()))
            .andExpect(jsonPath("$.isActive").value(newStatus.getIsActive()));
    }

    @Test
    void getStatusById() throws Exception {
        mockMvc.perform(get("/status/" + pendingStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Pending"))
            .andExpect(jsonPath("$.isActive").value(true));
    }

    @Test
    void getStatusByName() throws Exception {
        mockMvc.perform(get("/status/name/Shipped"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Shipped"))
            .andExpect(jsonPath("$.isActive").value(true));
    }

    @Test
    void updateStatus() throws Exception {
        Status updatedStatus = Status.builder()
            .name("Updated")
            .isActive(false)
            .build();
        mockMvc.perform(put("/status/" + pendingStatus.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedStatus)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value(updatedStatus.getName()))
            .andExpect(jsonPath("$.isActive").value(updatedStatus.getIsActive()));
    }

    @Test
    void deleteStatus() throws Exception {
        mockMvc.perform(delete("/status/" + pendingStatus.getId()))
            .andExpect(status().isNoContent());
    }

    @Test
    void deleteStatusNotFound() throws Exception {
        mockMvc.perform(delete("/status/9999"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Status with id 9999 not found"));
    }

    @Test
    void handleStatusNotFoundExceptionByName() throws Exception {
        mockMvc.perform(get("/status/name/Unexistent"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Status with name Unexistent not found"));
    }

    @Test
    void handleStatusNotFoundExceptionById() throws Exception {
        mockMvc.perform(get("/status/9999"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Status with id 9999 not found"));
    }
}
