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

    private static Status createStatus() {
        return Status.builder()
            .name("Pending")
            .active(true)
            .build();
    }

    @BeforeEach
    void setUp() {
        log.info("Setting up test data");
        Status status = createStatus();
        savedStatus = statusService.save(status);
        log.info("Saved status: {}", savedStatus);
    }

    @Test
    void findById() {
        Status statusFound = statusService.findById(savedStatus.getId()).orElse(null);

        assertNotNull(statusFound, "Status should not be null");
        assertEquals(savedStatus.getId(), statusFound.getId(), "IDs should match");
        assertEquals(savedStatus.getName(), statusFound.getName(), "Names should match");
        assertEquals(savedStatus.getActive(), statusFound.getActive(), "Active status should match");
    }

    @Test
    void findByName() {
        Status statusFound = statusService.findByName(savedStatus.getName()).orElse(null);

        assertNotNull(statusFound);
        assertEquals(savedStatus.getId(), statusFound.getId());
        assertEquals(savedStatus.getName(), statusFound.getName());
        assertEquals(savedStatus.getActive(), statusFound.getActive());
    }

    @Test
    void save() {
        assertNotNull(savedStatus);
        assertEquals("Pending", savedStatus.getName());
        assertEquals(true, savedStatus.getActive());
    }

    @Test
    void update() {
        Status updatedStatus = Status.builder()
            .id(savedStatus.getId())
            .name("Completed")
            .active(false)
            .build();

        Status statusUpdated = statusService.update(updatedStatus);

        assertNotNull(statusUpdated);
        assertEquals(savedStatus.getId(), statusUpdated.getId());
        assertEquals(updatedStatus.getName(), statusUpdated.getName());
        assertEquals(updatedStatus.getActive(), statusUpdated.getActive());
    }

    @Test
    void updateNotFound() {
        Status updatedStatus = Status.builder()
            .id(0L)
            .name("Completed")
            .active(false)
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
        Status status1 = createStatus();
        Status status2 = Status.builder()
            .name("Completed")
            .active(false)
            .build();

        statusService.save(status1);
        statusService.save(status2);

        assertEquals(3, statusService.findAll().size());
    }

    @Test
    void findAllByActive() {
        List<Status> activeStatuses = statusService.findAllByActive(true);
        assertFalse(activeStatuses.isEmpty(), "Active statuses should not be empty");
        for (Status status : activeStatuses) {
            assertTrue(status.getActive(), "Status should be active");
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
