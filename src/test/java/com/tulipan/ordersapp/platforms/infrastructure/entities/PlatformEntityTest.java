package com.tulipan.ordersapp.platforms.infrastructure.entities;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PlatformEntityTest {

    @Test
    void platformEntity_CreationWithBuilder() {
        PlatformEntity platformEntity = PlatformEntity.builder()
            .id(1L)
            .name("Platform1")
            .customerFee(new BigDecimal("10.0"))
            .sellerCommission(new BigDecimal("5.0"))
            .active(true)
            .build();

        assertNotNull(platformEntity);
        assertEquals(1L, platformEntity.getId());
        assertEquals("Platform1", platformEntity.getName());
        assertEquals(new BigDecimal("10.0"), platformEntity.getCustomerFee());
        assertEquals(new BigDecimal("5.0"), platformEntity.getSellerCommission());
        assertTrue(platformEntity.getActive());
    }

    @Test
    void platformEntity_DefaultActiveValue() {
        PlatformEntity platformEntity = PlatformEntity.builder()
            .id(1L)
            .name("Platform1")
            .customerFee(new BigDecimal("10.0"))
            .sellerCommission(new BigDecimal("5.0"))
            .build();

        platformEntity.prePersist();

        assertTrue(platformEntity.getActive());
    }

    @Test
    void platformEntity_SetActiveValue() {
        PlatformEntity platformEntity = PlatformEntity.builder()
            .id(1L)
            .name("Platform1")
            .customerFee(new BigDecimal("10.0"))
            .sellerCommission(new BigDecimal("5.0"))
            .active(false)
            .build();

        platformEntity.prePersist();

        assertFalse(platformEntity.getActive());
    }

    @Test
    void platformEntity_Equality() {
        PlatformEntity platformEntity1 = PlatformEntity.builder()
            .id(1L)
            .name("Platform1")
            .customerFee(new BigDecimal("10.0"))
            .sellerCommission(new BigDecimal("5.0"))
            .active(true)
            .build();

        PlatformEntity platformEntity2 = PlatformEntity.builder()
            .id(1L)
            .name("Platform1")
            .customerFee(new BigDecimal("10.0"))
            .sellerCommission(new BigDecimal("5.0"))
            .active(true)
            .build();

        assertEquals(platformEntity1, platformEntity2);
    }

    @Test
    void platformEntity_Inequality() {
        PlatformEntity platformEntity1 = PlatformEntity.builder()
            .id(1L)
            .name("Platform1")
            .customerFee(new BigDecimal("10.0"))
            .sellerCommission(new BigDecimal("5.0"))
            .active(true)
            .build();

        PlatformEntity platformEntity2 = PlatformEntity.builder()
            .id(2L)
            .name("Platform2")
            .customerFee(new BigDecimal("15.0"))
            .sellerCommission(new BigDecimal("7.0"))
            .active(false)
            .build();

        assertNotEquals(platformEntity1, platformEntity2);
    }

    @Test
    void platformEntity_SetActiveToNull() {
        PlatformEntity platformEntity = PlatformEntity.builder()
            .id(1L)
            .name("Platform1")
            .customerFee(new BigDecimal("10.0"))
            .sellerCommission(new BigDecimal("5.0"))
            .active(null)
            .build();

        platformEntity.prePersist();

        assertTrue(platformEntity.getActive());
    }

    @Test
    void platformEntity_GettersAndSetters() {
        PlatformEntity platformEntity = PlatformEntity.builder().build();
        platformEntity.setId(1L);
        platformEntity.setName("Platform1");
        platformEntity.setCustomerFee(new BigDecimal("10.0"));
        platformEntity.setSellerCommission(new BigDecimal("5.0"));
        platformEntity.setActive(true);

        assertEquals(1L, platformEntity.getId());
        assertEquals("Platform1", platformEntity.getName());
        assertEquals(new BigDecimal("10.0"), platformEntity.getCustomerFee());
        assertEquals(new BigDecimal("5.0"), platformEntity.getSellerCommission());
        assertTrue(platformEntity.getActive());
    }
}
