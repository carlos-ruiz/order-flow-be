package com.tulipan.ordersapp.orderitems.domain.service;

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
    // TODO List<OrderItem> findByOrderId(Long orderId);
    // TODO List<OrderItem> findBySellerId(Long sellerId);
}
