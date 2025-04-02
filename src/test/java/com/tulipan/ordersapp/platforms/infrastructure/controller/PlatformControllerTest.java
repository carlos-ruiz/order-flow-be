package com.tulipan.ordersapp.platforms.infrastructure.controller;

import com.tulipan.ordersapp.platforms.domain.model.Platform;
import com.tulipan.ordersapp.platforms.domain.service.PlatformService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PlatformControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PlatformService platformService;

    @InjectMocks
    private PlatformController platformController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(platformController).build();
    }

    @Test
    void createPlatform_ReturnsCreatedStatus() throws Exception {
        Platform platform = Platform.builder()
            .id(1L)
            .build();
        when(platformService.save(any(Platform.class))).thenReturn(platform);

        mockMvc.perform(post("/platforms")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Platform1\"}"))
            .andExpect(status().isCreated());
    }

    @Test
    void getPlatformById_ReturnsPlatform_WhenPlatformExists() throws Exception {
        Platform platform = Platform.builder()
            .id(1L)
            .build();
        when(platformService.findById(anyLong())).thenReturn(Optional.of(platform));

        mockMvc.perform(get("/platforms/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void getPlatformById_ReturnsNotFound_WhenPlatformDoesNotExist() throws Exception {
        when(platformService.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/platforms/1"))
            .andExpect(status().isNotFound());
    }

    @Test
    void getAllPlatforms_ReturnsOkStatus() throws Exception {
        mockMvc.perform(get("/platforms"))
            .andExpect(status().isOk());
    }

    @Test
    void updatePlatform_ReturnsUpdatedPlatform() throws Exception {
        Platform platform = Platform.builder()
            .id(1L)
            .build();
        when(platformService.update(any(Platform.class))).thenReturn(platform);

        mockMvc.perform(put("/platforms/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"UpdatedPlatform\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void deletePlatform_ReturnsNoContentStatus() throws Exception {
        Platform platform = Platform.builder()
            .id(1L)
            .build();
        when(platformService.findById(anyLong())).thenReturn(Optional.of(platform));

        mockMvc.perform(delete("/platforms/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    void deletePlatform_ReturnsNotFound_WhenPlatformDoesNotExist() throws Exception {
        when(platformService.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(delete("/platforms/1"))
            .andExpect(status().isNotFound());
    }
}
