package com.tulipan.ordersapp;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.byLessThan;
import static org.awaitility.Awaitility.await;

@SpringBootTest(classes = OrdersappApplication.class)
@TestPropertySource(properties = {
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
@Slf4j
class BaseEntityTest {

    @Autowired
    private SampleEntityRepository repository;

    @Test
    void shouldSetCreatedAtAndUpdatedAtOnPersist() {
        SampleEntity entity = new SampleEntity("Test Name");
        entity = repository.save(entity);

        assertThat(entity.getCreatedAt()).isNotNull();
        assertThat(entity.getUpdatedAt()).isNotNull();
    }

    @Test
    void shouldUpdateUpdatedAtOnEntityUpdate() {
        // Save the initial entity
        SampleEntity entity = repository.save(new SampleEntity("Initial Name"));
        LocalDateTime createdAt = entity.getCreatedAt();
        LocalDateTime updatedAt = entity.getUpdatedAt();
        log.info("Created at: {}", createdAt);
        log.info("Updated at: {}", updatedAt);

        // Update the entity and wait for the updatedAt timestamp to change
        entity.setName("Updated Name");
        await().atMost(1, TimeUnit.SECONDS).until(() -> {
            SampleEntity updatedEntity = repository.save(entity);
            return updatedEntity.getUpdatedAt().isAfter(updatedAt);
        });

        // Reload the entity to verify timestamps
        SampleEntity updatedEntity = repository.findById(entity.getId()).orElseThrow();
        log.info("AFTER UPDATE - Created at: {}", updatedEntity.getCreatedAt());
        log.info("AFTER UPDATE - Updated at: {}", updatedEntity.getUpdatedAt());

        // Assertions
        assertThat(updatedEntity.getCreatedAt()).isCloseTo(createdAt, byLessThan(1, ChronoUnit.MICROS));
        assertThat(updatedEntity.getUpdatedAt()).isAfter(updatedAt);
    }
}
