package com.tulipan.ordersapp.platforms.application;

import com.tulipan.ordersapp.platforms.domain.model.Platform;
import com.tulipan.ordersapp.platforms.infrastructure.repository.PlatformRepositoryAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlatformServiceImplTest {

    @Mock
    private PlatformRepositoryAdapter repository;

    @InjectMocks
    private PlatformServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_ReturnsPlatform_WhenPlatformExists() {
        Platform platform = Platform.builder().build();
        platform.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(platform));

        Optional<Platform> result = service.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void findById_ReturnsEmpty_WhenPlatformDoesNotExist() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        Optional<Platform> result = service.findById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    void save_ReturnsSavedPlatform() {
        Platform platform = Platform.builder().build();
        when(repository.save(platform)).thenReturn(platform);

        Platform result = service.save(platform);

        assertNotNull(result);
    }

    @Test
    void delete_DeletesPlatform() {
        Platform platform = Platform.builder().build();
        doNothing().when(repository).delete(platform);

        service.delete(platform);

        verify(repository, times(1)).delete(platform);
    }

    @Test
    void deleteById_DeletesPlatformById() {
        doNothing().when(repository).deleteById(1L);

        service.deleteById(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void update_ReturnsUpdatedPlatform() {
        Platform platform = Platform.builder().build();
        when(repository.update(platform)).thenReturn(platform);

        Platform result = service.update(platform);

        assertNotNull(result);
    }

    @Test
    void findAll_ReturnsListOfPlatforms() {
        Platform platform1 = Platform.builder().build();
        Platform platform2 = Platform.builder().build();
        when(repository.findAll()).thenReturn(Arrays.asList(platform1, platform2));

        List<Platform> result = service.findAll();

        assertEquals(2, result.size());
    }
}
