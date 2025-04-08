package com.tulipan.ordersapp.orderitems.infrastructure.repository;

import com.tulipan.ordersapp.orderitems.infrastructure.entities.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderItemRepository extends JpaRepository<OrderItemEntity, Long> {
}
