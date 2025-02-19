package com.tulipan.ordersapp.sellers.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tulipan.ordersapp.sellers.infrastructure.entities.PlatformEntity;

@Repository
public interface PlatformRepository extends JpaRepository<PlatformEntity, Long> {
}
