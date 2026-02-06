package com.tulipan.ordersapp.status.infrastructure.repository;

import com.tulipan.ordersapp.status.infrastructure.entities.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaStatusRepository extends JpaRepository<StatusEntity, Long> {
    Optional<StatusEntity> findByName(String name);

    List<StatusEntity> findAllByActive(Boolean active);
}
