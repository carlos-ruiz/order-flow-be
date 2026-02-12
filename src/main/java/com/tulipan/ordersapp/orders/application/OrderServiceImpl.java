package com.tulipan.ordersapp.orders.application;

import com.tulipan.ordersapp.orders.domain.model.Order;
import com.tulipan.ordersapp.orders.domain.repository.OrderRepository;
import com.tulipan.ordersapp.platforms.application.PlatformService;
import com.tulipan.ordersapp.platforms.domain.exceptions.PlatformNotFoundException;
import com.tulipan.ordersapp.platforms.domain.model.Platform;
import com.tulipan.ordersapp.status.application.StatusService;
import com.tulipan.ordersapp.status.domain.exceptions.StatusNotFoundException;
import com.tulipan.ordersapp.status.domain.model.Status;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;
    private final PlatformService platformService;
    private final StatusService statusService;

    public OrderServiceImpl(OrderRepository repository, PlatformService platformService, StatusService statusService) {
        this.repository = repository;
        this.platformService = platformService;
        this.statusService = statusService;
    }

    @Override
    public Order save(Order order) {
        return repository.save(order);
    }

    @Override
    public Order save(LocalDateTime dateTime, BigDecimal discount, Long platformId, Long statusId) {
        if (platformId == null) {
            throw new IllegalArgumentException("Platform ID cannot be null");
        }

        if (statusId == null) {
            throw new IllegalArgumentException("Status ID cannot be null");
        }

        Platform platform = platformService.findById(platformId)
            .orElseThrow(() -> new PlatformNotFoundException(platformId));

        Status status = statusService.findById(statusId)
            .orElseThrow(() -> new StatusNotFoundException(statusId));

        Order order = Order.builder()
            .dateTime(dateTime)
            .discount(discount)
            .platform(platform)
            .status(status)
            .build();

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
