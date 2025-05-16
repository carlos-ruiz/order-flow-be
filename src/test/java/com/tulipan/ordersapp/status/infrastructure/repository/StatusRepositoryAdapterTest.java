package com.tulipan.ordersapp.status.infrastructure.repository;

import com.tulipan.ordersapp.status.domain.model.Status;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class StatusRepositoryAdapterTest {
    @Autowired
    private StatusRepositoryAdapter statusRepositoryAdapter;

    @Test
    void findById() {
        Status statusEntity = Status.builder()
            .name("Pending")
            .isActive(true)
            .build();

        Status savedEntity = statusRepositoryAdapter.save(statusEntity);
        Optional<Status> statusFound = statusRepositoryAdapter.findById(savedEntity.getId());
        log.info("Status found FIND BY ID: {}", statusFound);
        assert statusFound.isPresent();
        assertEquals(savedEntity.getId(), statusFound.get().getId());
        assertEquals("Pending", statusFound.get().getName());
        assertEquals(true, statusFound.get().getIsActive());
    }

    @Test
    void findByName() {
        String name = "Pending";
        Status entity = Status.builder()
            .name(name)
            .isActive(true)
            .build();
        statusRepositoryAdapter.save(entity);

        Optional<Status> statusFound = statusRepositoryAdapter.findByName(name);
        log.info("Status found: {}", statusFound);

        assert statusFound.isPresent();
        assertEquals(name, statusFound.get().getName());
        assertEquals(true, statusFound.get().getIsActive());
    }

    @Test
    void save() {
        Status status = Status.builder()
            .name("Pending")
            .isActive(true)
            .build();

        Status saved = statusRepositoryAdapter.save(status);
        log.info("Saved entity: {}", saved);

        assertNotNull(saved.getId());
        assertEquals(status.getName(), saved.getName());
        assertEquals(status.getIsActive(), saved.getIsActive());
    }

    @Test
    void deleteById() {
        Status status = Status.builder()
            .name("Pending")
            .isActive(true)
            .build();

        Status saved = statusRepositoryAdapter.save(status);
        statusRepositoryAdapter.deleteById(saved.getId());
        Optional<Status> statusFound = statusRepositoryAdapter.findById(saved.getId());

        assertFalse(statusFound.isPresent(), "Status should be deleted");
    }

    @Test
    void delete() {
        Status status = Status.builder()
            .name("Pending")
            .isActive(true)
            .build();

        Status saved = statusRepositoryAdapter.save(status);
        statusRepositoryAdapter.delete(saved);
        Optional<Status> statusFound = statusRepositoryAdapter.findById(saved.getId());

        assertFalse(statusFound.isPresent(), "Status should be deleted");
    }

    @Test
    void findAll() {
        Status statusEntity1 = Status.builder()
            .name("Pending")
            .isActive(true)
            .build();
        Status statusEntity2 = Status.builder()
            .name("Completed")
            .isActive(false)
            .build();

        statusRepositoryAdapter.save(statusEntity1);
        statusRepositoryAdapter.save(statusEntity2);

        List<Status> allStatuses = statusRepositoryAdapter.findAll();
        log.info("All statuses: {}", allStatuses);

        assertEquals(2, allStatuses.size());
    }

    @Test
    void findAllByIsActive() {
        Status statusEntity1 = Status.builder()
            .name("Pending")
            .isActive(true)
            .build();
        Status statusEntity2 = Status.builder()
            .name("Completed")
            .isActive(false)
            .build();

        statusRepositoryAdapter.save(statusEntity1);
        statusRepositoryAdapter.save(statusEntity2);

        List<Status> activeStatuses = statusRepositoryAdapter.findAllByIsActive(true);
        log.info("Active statuses: {}", activeStatuses);

        assertEquals(1, activeStatuses.size());
        assertEquals("Pending", activeStatuses.getFirst().getName());
    }

    @Test
    void findAllByIsInactive() {
        Status statusEntity1 = Status.builder()
            .name("Pending")
            .isActive(true)
            .build();
        Status statusEntity2 = Status.builder()
            .name("Completed")
            .isActive(false)
            .build();

        statusRepositoryAdapter.save(statusEntity1);
        statusRepositoryAdapter.save(statusEntity2);

        List<Status> inactiveStatuses = statusRepositoryAdapter.findAllByIsActive(false);
        log.info("Inactive statuses: {}", inactiveStatuses);

        assertEquals(1, inactiveStatuses.size());
        assertEquals("Completed", inactiveStatuses.getFirst().getName());
    }

    @Test
    void findAllByIsActive_EmptyList() {
        List<Status> activeStatuses = statusRepositoryAdapter.findAllByIsActive(true);
        log.info("Active statuses: {}", activeStatuses);

        assertTrue(activeStatuses.isEmpty(), "Expected empty list of active statuses");
    }
}
