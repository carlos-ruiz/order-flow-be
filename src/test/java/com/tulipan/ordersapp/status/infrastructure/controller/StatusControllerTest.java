package com.tulipan.ordersapp.status.infrastructure.controller;

import com.tulipan.ordersapp.status.application.StatusService;
import com.tulipan.ordersapp.status.domain.model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class StatusControllerTest {

    private MockMvc mockMvc;

    @Mock
    private StatusService statusService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new StatusController(statusService)).build();
    }

    @Test
    void getAllStatuses() throws Exception {
        List<Status> statuses = List.of(
            new Status(1L, "Pending", true),
            new Status(2L, "Shipped", true),
            new Status(3L, "Delivered", true)
        );
        when(statusService.findAll()).thenReturn(statuses);

        mockMvc.perform(get("/status"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(3))
            .andExpect(jsonPath("$[0].name").value("Pending"))
            .andExpect(jsonPath("$[1].name").value("Shipped"))
            .andExpect(jsonPath("$[2].name").value("Delivered"));
    }

    @Test
    void createStatus() throws Exception {
        Status newStatus = new Status(null, "Cancelled", false);
        Status savedStatus = new Status(4L, "Cancelled", false);
        when(statusService.save(newStatus)).thenReturn(savedStatus);

        mockMvc.perform(post("/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Cancelled\",\"active\":false}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(4))
            .andExpect(jsonPath("$.name").value("Cancelled"))
            .andExpect(jsonPath("$.active").value(false));
    }

    @Test
    void getStatusById() throws Exception {
        Status status = new Status(1L, "Pending", true);
        when(statusService.findById(1L)).thenReturn(Optional.of(status));

        mockMvc.perform(get("/status/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Pending"))
            .andExpect(jsonPath("$.active").value(true));
    }

    @Test
    void getStatusById_NotFound() throws Exception {
        when(statusService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/status/1"))
            .andExpect(status().isNotFound());
    }

    @Test
    void deleteStatus_Success() throws Exception {
        when(statusService.existsById(1L)).thenReturn(true);

        mockMvc.perform(delete("/status/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    void deleteStatus_NotFound() throws Exception {
        when(statusService.existsById(1L)).thenReturn(false);

        mockMvc.perform(delete("/status/1"))
            .andExpect(status().isNotFound());
    }

    @Test
    void findStatusByName() throws Exception {
        Status status = new Status(1L, "Pending", true);
        when(statusService.findByName("Pending")).thenReturn(Optional.of(status));

        mockMvc.perform(get("/status/name/Pending"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Pending"))
            .andExpect(jsonPath("$.active").value(true));
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

    @Test
    void updateStatus() throws Exception {
        Status updatedStatus = new Status(1L, "Updated", true);
        when(statusService.update(updatedStatus)).thenReturn(updatedStatus);

        mockMvc.perform(put("/status/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"name\":\"Updated\",\"active\":true}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Updated"))
            .andExpect(jsonPath("$.active").value(true));
    }

    @Test
    void getAllActiveStatuses() throws Exception {
        List<Status> activeStatuses = List.of(
            new Status(1L, "Pending", true),
            new Status(2L, "Shipped", true)
        );
        when(statusService.findAllByActive(true)).thenReturn(activeStatuses);

        mockMvc.perform(get("/status/active"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].name").value("Pending"))
            .andExpect(jsonPath("$[1].name").value("Shipped"));
    }

    @Test
    void getAllInactiveStatuses() throws Exception {
        List<Status> inactiveStatuses = List.of(
            new Status(3L, "Cancelled", false),
            new Status(4L, "Returned", false)
        );
        when(statusService.findAllByActive(false)).thenReturn(inactiveStatuses);

        mockMvc.perform(get("/status/inactive"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].name").value("Cancelled"))
            .andExpect(jsonPath("$[1].name").value("Returned"));
    }
}
