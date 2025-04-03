package com.tulipan.ordersapp.platforms.infrastructure.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlatformEntityTest {

    @Test
    void platformEntity_CreationWithBuilder() {
        PlatformEntity platformEntity = PlatformEntity.builder()
            .id(1L)
            .name("Platform1")
            .customerFee(10.0f)
            .sellerCommission(5.0f)
            .active(true)
            .build();

        assertNotNull(platformEntity);
        assertEquals(1L, platformEntity.getId());
        assertEquals("Platform1", platformEntity.getName());
        assertEquals(10.0f, platformEntity.getCustomerFee());
        assertEquals(5.0f, platformEntity.getSellerCommission());
        assertTrue(platformEntity.getActive());
    }

    @Test
    void platformEntity_DefaultActiveValue() {
        PlatformEntity platformEntity = PlatformEntity.builder()
            .id(1L)
            .name("Platform1")
            .customerFee(10.0f)
            .sellerCommission(5.0f)
            .build();

        platformEntity.prePersist();

        assertTrue(platformEntity.getActive());
    }

    @Test
    void platformEntity_SetActiveValue() {
        PlatformEntity platformEntity = PlatformEntity.builder()
            .id(1L)
            .name("Platform1")
            .customerFee(10.0f)
            .sellerCommission(5.0f)
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
            .customerFee(10.0f)
            .sellerCommission(5.0f)
            .active(true)
            .build();

        PlatformEntity platformEntity2 = PlatformEntity.builder()
            .id(1L)
            .name("Platform1")
            .customerFee(10.0f)
            .sellerCommission(5.0f)
            .active(true)
            .build();

        assertEquals(platformEntity1, platformEntity2);
    }

    @Test
    void platformEntity_Inequality() {
        PlatformEntity platformEntity1 = PlatformEntity.builder()
            .id(1L)
            .name("Platform1")
            .customerFee(10.0f)
            .sellerCommission(5.0f)
            .active(true)
            .build();

        PlatformEntity platformEntity2 = PlatformEntity.builder()
            .id(2L)
            .name("Platform2")
            .customerFee(15.0f)
            .sellerCommission(7.0f)
            .active(false)
            .build();

        assertNotEquals(platformEntity1, platformEntity2);
    }

    @Test
    void platformEntity_SetActiveToNull() {
        PlatformEntity platformEntity = PlatformEntity.builder()
            .id(1L)
            .name("Platform1")
            .customerFee(10.0f)
            .sellerCommission(5.0f)
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
        platformEntity.setCustomerFee(10.0f);
        platformEntity.setSellerCommission(5.0f);
        platformEntity.setActive(true);

        assertEquals(1L, platformEntity.getId());
        assertEquals("Platform1", platformEntity.getName());
        assertEquals(10.0f, platformEntity.getCustomerFee());
        assertEquals(5.0f, platformEntity.getSellerCommission());
        assertTrue(platformEntity.getActive());
    }
}
