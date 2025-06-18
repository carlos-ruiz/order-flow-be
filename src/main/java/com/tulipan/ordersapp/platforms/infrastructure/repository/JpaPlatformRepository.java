package com.tulipan.ordersapp.platforms.infrastructure.repository;

import com.tulipan.ordersapp.platforms.infrastructure.entities.PlatformEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaPlatformRepository extends JpaRepository<PlatformEntity, Long> {
}
