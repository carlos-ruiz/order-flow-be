package com.tulipan.ordersapp.orders.application;

import com.tulipan.ordersapp.orders.domain.model.Order;
import com.tulipan.ordersapp.orders.domain.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;

    public OrderServiceImpl(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Order save(Order order) {
        return repository.save(order);
    }

    @Override
    public Order update(Order order) {
        return repository.update(order);
    }

    @Override
    public void delete(Order order) {
        repository.delete(order);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Order> findAll() {
        return repository.findAll();
    }
}
