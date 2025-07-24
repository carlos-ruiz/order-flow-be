package com.tulipan.ordersapp.orders.application;

import com.tulipan.ordersapp.orders.domain.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order save(Order order);

    Order update(Order order);

    void delete(Order order);

    void deleteById(Long id);

    Optional<Order> findById(Long id);

    List<Order> findAll();
}
