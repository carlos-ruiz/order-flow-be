package com.tulipan.ordersapp.orderitems.application;

import com.tulipan.ordersapp.orderitems.domain.model.OrderItem;

import java.util.List;
import java.util.Optional;

public interface OrderItemService {
    OrderItem save(OrderItem orderItem);

    OrderItem update(OrderItem orderItem);

    void delete(OrderItem orderItem);

    void deleteById(Long id);

    Optional<OrderItem> findById(Long id);

    List<OrderItem> findAll();

    List<OrderItem> findByOrderId(Long orderId);

    List<OrderItem> findBySellerId(Long sellerId);
}
