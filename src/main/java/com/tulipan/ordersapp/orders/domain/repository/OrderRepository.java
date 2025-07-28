package com.tulipan.ordersapp.orders.domain.repository;

import com.tulipan.ordersapp.orders.domain.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Optional<Order> findById(Long id);

    Order save(Order order);

    Order update(Order order);

    void deleteById(Long id);

    void delete(Order order);

    List<Order> findAll();
}
