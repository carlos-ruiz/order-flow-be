package com.tulipan.ordersapp.platforms.infrastructure.converters;

import com.tulipan.ordersapp.platforms.domain.model.Platform;
import com.tulipan.ordersapp.platforms.infrastructure.entities.PlatformEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PlatformConverterTest {

    @Test
    void convertsPlatformEntityToModelSuccessfully() {
        PlatformEntity platformEntity = new PlatformEntity();
        platformEntity.setId(1L);
        platformEntity.setName("Platform A");
        platformEntity.setCustomerFee(new BigDecimal("2.5"));
        platformEntity.setSellerCommission(new BigDecimal("10.0"));
        platformEntity.setActive(true);

        Platform platform = PlatformConverter.toModel(platformEntity);

        assertNotNull(platform);
        assertEquals(1L, platform.getId());
        assertEquals("Platform A", platform.getName());
        assertEquals(new BigDecimal("2.5"), platform.getCustomerFee());
        assertEquals(new BigDecimal("10.0"), platform.getSellerCommission());
        assertTrue(platform.getActive());
    }

    @Test
    void returnsNullWhenPlatformEntityIsNull() {
        Platform platform = PlatformConverter.toModel(null);
        assertNull(platform);
    }

    @Test
    void convertsPlatformToEntitySuccessfully() {
        Platform platform = new Platform();
        platform.setId(1L);
        platform.setName("Platform B");
        platform.setCustomerFee(new BigDecimal("3.0"));
        platform.setSellerCommission(new BigDecimal("12.0"));
        platform.setActive(false);

        PlatformEntity platformEntity = PlatformConverter.toEntity(platform);

        assertNotNull(platformEntity);
        assertEquals(1L, platformEntity.getId());
        assertEquals("Platform B", platformEntity.getName());
        assertEquals(new BigDecimal("3.0"), platformEntity.getCustomerFee());
        assertEquals(new BigDecimal("12.0"), platformEntity.getSellerCommission());
        assertFalse(platformEntity.getActive());
    }

    @Test
    void returnsNullWhenPlatformIsNull() {
        PlatformEntity platformEntity = PlatformConverter.toEntity(null);
        assertNull(platformEntity);
    }
}
