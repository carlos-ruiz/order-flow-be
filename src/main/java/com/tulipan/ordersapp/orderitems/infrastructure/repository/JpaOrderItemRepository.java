package com.tulipan.ordersapp.orderitems.infrastructure.repository;

import com.tulipan.ordersapp.orderitems.infrastructure.entities.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaOrderItemRepository extends JpaRepository<OrderItemEntity, Long> {
    List<OrderItemEntity> findAllBySeller(Long sellerId);

    List<OrderItemEntity> findAllByOrder(Long orderId);

    List<OrderItemEntity> findAllByCustomer(Long customerId);
}
