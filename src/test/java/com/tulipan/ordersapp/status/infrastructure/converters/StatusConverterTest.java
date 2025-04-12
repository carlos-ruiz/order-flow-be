package com.tulipan.ordersapp.status.infrastructure.converters;

import com.tulipan.ordersapp.status.domain.model.Status;
import com.tulipan.ordersapp.status.infrastructure.entities.StatusEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusConverterTest {

    @Test
    void toModelSuccess() {
        StatusEntity statusEntity = new StatusEntity();
        statusEntity.setId(1L);
        statusEntity.setName("Pending");
        statusEntity.setIsActive(true);

        Status status = StatusConverter.toModel(statusEntity);

        assertNotNull(status);
        assertEquals(1L, status.getId());
        assertEquals("Pending", status.getName());
        assertTrue(status.getIsActive());
    }

    @Test
    void toModelNull() {
        Status status = StatusConverter.toModel(null);
        assertNull(status);
    }

    @Test
    void toEntitySuccess() {
        Status status = new Status();
        status.setId(2L);
        status.setName("Shipped");
        status.setIsActive(false);

        StatusEntity statusEntity = StatusConverter.toEntity(status);

        assertNotNull(statusEntity);
        assertEquals(2L, statusEntity.getId());
        assertEquals("Shipped", statusEntity.getName());
        assertFalse(statusEntity.getIsActive());
    }

    @Test
    void toEntityNull() {
        StatusEntity statusEntity = StatusConverter.toEntity(null);
        assertNull(statusEntity);
    }
}
