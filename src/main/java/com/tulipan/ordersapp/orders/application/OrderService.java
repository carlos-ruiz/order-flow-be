package com.tulipan.ordersapp.orders.application;

import com.tulipan.ordersapp.orders.domain.model.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order save(Order order);

    Order save(LocalDateTime dateTime, BigDecimal discount, Long platformId);

    Order update(Order order);

    void delete(Order order);

    void deleteById(Long id);

    Optional<Order> findById(Long id);

    List<Order> findAll();
}
