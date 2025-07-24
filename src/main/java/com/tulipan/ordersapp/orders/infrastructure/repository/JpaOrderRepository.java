package com.tulipan.ordersapp.orders.infrastructure.repository;

import com.tulipan.ordersapp.orders.infrastructure.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaOrderRepository extends JpaRepository<OrderEntity, Long> {
}
