package com.tulipan.ordersapp;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleEntityRepository extends JpaRepository<SampleEntity, Long> {
}
