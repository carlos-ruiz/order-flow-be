package com.tulipan.ordersapp.status.application;

import com.tulipan.ordersapp.status.domain.exceptions.StatusNotFoundException;
import com.tulipan.ordersapp.status.domain.model.Status;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
class StatusServiceImplTest {

    @Autowired
    private StatusServiceImpl statusService;

    private Status savedStatus;

    @BeforeEach
    void setUp() {
        log.info("Setting up test data");
        Status status = Status.builder()
            .name("Pending")
            .isActive(true)
            .build();
        savedStatus = statusService.save(status);
        log.info("Saved status: {}", savedStatus);
    }

    @Test
    void findById() {
        Status statusFound = statusService.findById(savedStatus.getId()).orElse(null);

        assertNotNull(statusFound, "Status should not be null");
        assertEquals(savedStatus.getId(), statusFound.getId(), "IDs should match");
        assertEquals(savedStatus.getName(), statusFound.getName(), "Names should match");
        assertEquals(savedStatus.getIsActive(), statusFound.getIsActive(), "Active status should match");
    }

    @Test
    void findByName() {
        Status statusFound = statusService.findByName(savedStatus.getName()).orElse(null);

        assertNotNull(statusFound);
        assertEquals(savedStatus.getId(), statusFound.getId());
        assertEquals(savedStatus.getName(), statusFound.getName());
        assertEquals(savedStatus.getIsActive(), statusFound.getIsActive());
    }

    @Test
    void save() {
        assertNotNull(savedStatus);
        assertEquals("Pending", savedStatus.getName());
        assertEquals(true, savedStatus.getIsActive());
    }

    @Test
    void update() {
        Status updatedStatus = Status.builder()
            .id(savedStatus.getId())
            .name("Completed")
            .isActive(false)
            .build();

        Status statusUpdated = statusService.update(updatedStatus);

        assertNotNull(statusUpdated);
        assertEquals(savedStatus.getId(), statusUpdated.getId());
        assertEquals(updatedStatus.getName(), statusUpdated.getName());
        assertEquals(updatedStatus.getIsActive(), statusUpdated.getIsActive());
    }

    @Test
    void updateNotFound() {
        Status updatedStatus = Status.builder()
            .id(0L)
            .name("Completed")
            .isActive(false)
            .build();

        assertThrows(StatusNotFoundException.class, () -> statusService.update(updatedStatus));
    }

    @Test
    void deleteById() {
        statusService.deleteById(savedStatus.getId());
        Status statusFound = statusService.findById(savedStatus.getId()).orElse(null);

        assertNull(statusFound);
    }

    @Test
    void delete() {
        statusService.delete(savedStatus);
        Status statusFound = statusService.findById(savedStatus.getId()).orElse(null);
        assertNull(statusFound);
    }

    @Test
    void findAll() {
        Status status1 = Status.builder()
            .name("Pending")
            .isActive(true)
            .build();
        Status status2 = Status.builder()
            .name("Completed")
            .isActive(false)
            .build();

        statusService.save(status1);
        statusService.save(status2);

        assertEquals(3, statusService.findAll().size());
    }

    @Test
    void findAllByIsActive() {
        List<Status> activeStatuses = statusService.findAllByIsActive(true);
        assertFalse(activeStatuses.isEmpty(), "Active statuses should not be empty");
        for (Status status : activeStatuses) {
            assertTrue(status.getIsActive(), "Status should be active");
        }
        assertEquals(1, activeStatuses.size(), "There should be one active status");

    }

    @Test
    void existsById() {
        boolean exists = statusService.existsById(savedStatus.getId());
        assertTrue(exists, "Status should exist by ID");

        boolean notExists = statusService.existsById(0L);
        assertFalse(notExists, "Status should not exist for non-existing ID");
    }
}
