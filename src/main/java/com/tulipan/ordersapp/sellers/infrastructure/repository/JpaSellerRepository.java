package com.tulipan.ordersapp.sellers.infrastructure.repository;

import com.tulipan.ordersapp.sellers.infrastructure.entities.SellerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaSellerRepository extends JpaRepository<SellerEntity, Long> {
}
