package com.tulipan.ordersapp.orders.infrastructure.repository;

import com.tulipan.ordersapp.orderitems.domain.model.OrderItem;
import com.tulipan.ordersapp.orderitems.infrastructure.converters.OrderItemConverter;
import com.tulipan.ordersapp.orderitems.infrastructure.entities.OrderItemEntity;
import com.tulipan.ordersapp.orders.domain.exceptions.OrderNotFoundException;
import com.tulipan.ordersapp.orders.domain.model.Order;
import com.tulipan.ordersapp.orders.domain.repository.OrderRepository;
import com.tulipan.ordersapp.orders.infrastructure.converters.OrderConverter;
import com.tulipan.ordersapp.orders.infrastructure.entities.OrderEntity;
import com.tulipan.ordersapp.platforms.domain.model.Platform;
import com.tulipan.ordersapp.platforms.infrastructure.converters.PlatformConverter;
import com.tulipan.ordersapp.platforms.infrastructure.entities.PlatformEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepositoryAdapter implements OrderRepository {

    private final JpaOrderRepository jpaOrderRepository;

    public OrderRepositoryAdapter(JpaOrderRepository jpaOrderRepository) {
        this.jpaOrderRepository = jpaOrderRepository;
    }

    protected Order toModel(OrderEntity orderEntity) {
        if (orderEntity == null) {
            return null;
        }

        Platform platform = PlatformConverter.toModel(orderEntity.getPlatform());
        List<OrderItem> items = List.of();
        if (orderEntity.getOrderItems() != null) {
            items = orderEntity.getOrderItems().stream().map(OrderItemConverter::toModel).toList();
        }
        return new Order(
            orderEntity.getId(),
            orderEntity.getDateTime(),
            orderEntity.getDiscount(),
            platform,
            items
        );
    }

    protected OrderEntity toEntity(Order order) {
        if (order == null) {
            return null;
        }

        PlatformEntity platformEntity = PlatformConverter.toEntity(order.getPlatform());
        List<OrderItemEntity> items = List.of();
        if (order.getOrderItems() != null) {
            items = order.getOrderItems().stream().map(OrderItemConverter::toEntity).toList();
        }
        return new OrderEntity(
            order.getId(),
            order.getDateTime(),
            order.getDiscount(),
            platformEntity,
            items
        );
    }

    @Override
    public Optional<Order> findById(Long id) {
        return jpaOrderRepository.findById(id)
            .map(this::toModel);
    }

    @Override
    public Order save(Order order) {
        return toModel(jpaOrderRepository.save(toEntity(order)));
    }

    @Override
    public Order update(Order order) {
        return toModel(jpaOrderRepository.save(toEntity(order)));
    }

    @Override
    public void deleteById(Long id) {
        if (!jpaOrderRepository.existsById(id)) {
            throw new OrderNotFoundException(id);
        }
        jpaOrderRepository.deleteById(id);
    }

    @Override
    public void delete(Order order) {
        if (!jpaOrderRepository.existsById(order.getId())) {
            throw new OrderNotFoundException(order.getId());
        }
        jpaOrderRepository.delete(toEntity(order));
    }

    @Override
    public List<Order> findAll() {
        return jpaOrderRepository.findAll().stream().map(OrderConverter::toModel).toList();
    }
}
