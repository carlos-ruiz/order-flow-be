package com.tulipan.ordersapp.platforms.infrastructure.repository;

import com.tulipan.ordersapp.platforms.domain.exceptions.PlatformNotFoundException;
import com.tulipan.ordersapp.platforms.domain.model.Platform;
import com.tulipan.ordersapp.platforms.infrastructure.entities.PlatformEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class JpaPlatformRepositoryAdapterTest {

    @Mock
    private JpaPlatformRepository jpaPlatformRepository;

    @InjectMocks
    private PlatformRepositoryAdapter platformRepositoryAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void savePlatform_Success() {
        Platform platform = new Platform(1L, "Platform1", new BigDecimal("10.0"), new BigDecimal("5.0"), true);
        PlatformEntity platformEntity = new PlatformEntity(1L, "Platform1", new BigDecimal("10.0"), new BigDecimal("5.0"), true);

        when(jpaPlatformRepository.save(any(PlatformEntity.class))).thenReturn(platformEntity);

        Platform savedPlatform = platformRepositoryAdapter.save(platform);

        assertNotNull(savedPlatform);
        assertEquals(platform.getId(), savedPlatform.getId());
    }

    @Test
    void findById_PlatformExists() {
        PlatformEntity platformEntity = new PlatformEntity(1L, "Platform1", new BigDecimal("10.0"), new BigDecimal("5.0"), true);

        when(jpaPlatformRepository.findById(anyLong())).thenReturn(Optional.of(platformEntity));

        Optional<Platform> platform = platformRepositoryAdapter.findById(1L);

        assertTrue(platform.isPresent());
        assertEquals(platformEntity.getId(), platform.get().getId());
    }

    @Test
    void findById_PlatformDoesNotExist() {
        when(jpaPlatformRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<Platform> platform = platformRepositoryAdapter.findById(1L);

        assertFalse(platform.isPresent());
    }

    @Test
    void findAllPlatforms_Success() {
        List<PlatformEntity> platformEntities = List.of(
            new PlatformEntity(1L, "Platform1", new BigDecimal("10.0"), new BigDecimal("5.0"), true),
            new PlatformEntity(2L, "Platform2", new BigDecimal("15.0"), new BigDecimal("7.0"), false)
        );

        when(jpaPlatformRepository.findAll()).thenReturn(platformEntities);

        List<Platform> platforms = platformRepositoryAdapter.findAll();

        assertNotNull(platforms);
        assertEquals(2, platforms.size());
    }

    @Test
    void deletePlatform_Success() {
        Platform platform = new Platform(1L, "Platform1", new BigDecimal("10.0"), new BigDecimal("5.0"), true);

        doNothing().when(jpaPlatformRepository).delete(any(PlatformEntity.class));

        platformRepositoryAdapter.delete(platform);

        verify(jpaPlatformRepository, times(1)).delete(any(PlatformEntity.class));
    }

    @Test
    void deleteById_Success() {
        doNothing().when(jpaPlatformRepository).deleteById(anyLong());

        platformRepositoryAdapter.deleteById(1L);

        verify(jpaPlatformRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void updatePlatform_Success() {
        Platform platform = new Platform(1L, "UpdatedPlatform", new BigDecimal("12.0"), new BigDecimal("6.0"), true);
        PlatformEntity platformEntity = new PlatformEntity(1L, "Platform1", new BigDecimal("10.0"), new BigDecimal("5.0"), true);

        when(jpaPlatformRepository.findById(anyLong())).thenReturn(Optional.of(platformEntity));
        when(jpaPlatformRepository.save(any(PlatformEntity.class))).thenReturn(platformEntity);

        Platform updatedPlatform = platformRepositoryAdapter.update(platform);

        assertNotNull(updatedPlatform);
        assertEquals(platform.getName(), updatedPlatform.getName());
    }

    @Test
    void updatePlatform_NotFound() {
        Platform platform = new Platform(1L, "UpdatedPlatform", new BigDecimal("12.0"), new BigDecimal("6.0"), true);

        when(jpaPlatformRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(PlatformNotFoundException.class, () -> platformRepositoryAdapter.update(platform));
    }

    @Test
    void updatePlatform_ActiveNull() {
        Platform platform = new Platform(1L, "UpdatedPlatform", new BigDecimal("12.0"), new BigDecimal("6.0"), null);
        PlatformEntity platformEntity = new PlatformEntity(1L, "Platform1", new BigDecimal("10.0"), new BigDecimal("5.0"), null);

        when(jpaPlatformRepository.findById(anyLong())).thenReturn(Optional.of(platformEntity));
        when(jpaPlatformRepository.save(any(PlatformEntity.class))).thenReturn(platformEntity);

        Platform updatedPlatform = platformRepositoryAdapter.update(platform);

        assertNotNull(updatedPlatform);
        assertEquals(platform.getName(), updatedPlatform.getName());
        assertTrue(updatedPlatform.getActive());
    }
}
