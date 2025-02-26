package com.tulipan.ordersapp;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
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
    void shouldUpdateUpdatedAtOnEntityUpdate() throws InterruptedException {
        SampleEntity entity = new SampleEntity("Initial Name");
        entity = repository.save(entity);
        LocalDateTime createdAt = entity.getCreatedAt();
        LocalDateTime updatedAt = entity.getUpdatedAt();
        log.info("Created at: {}", createdAt);
        log.info("Updated at: {}", updatedAt);

        Thread.sleep(1000);
        entity.setName("Updated Name");
        entity = repository.save(entity);
        log.info("AFTER UPDATE - Created at: {}", entity.getCreatedAt());
        log.info("AFTER UPDATE - Updated at: {}", entity.getUpdatedAt());

        assertThat(entity.getCreatedAt()).isEqualTo(createdAt);
        assertThat(entity.getUpdatedAt()).isAfter(updatedAt);
    }
}