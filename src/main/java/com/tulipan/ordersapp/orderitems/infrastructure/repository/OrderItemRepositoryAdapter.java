package com.tulipan.ordersapp.orderitems.infrastructure.repository;

import com.tulipan.ordersapp.orderitems.domain.exceptions.OrderItemNotFoundException;
import com.tulipan.ordersapp.orderitems.domain.model.OrderItem;
import com.tulipan.ordersapp.orderitems.domain.repository.OrderItemRepository;
import com.tulipan.ordersapp.orderitems.infrastructure.converters.OrderItemConverter;
import com.tulipan.ordersapp.orderitems.infrastructure.entities.OrderItemEntity;
import com.tulipan.ordersapp.sellers.infrastructure.converters.SellerConverter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OrderItemRepositoryAdapter implements OrderItemRepository {

    private final JpaOrderItemRepository jpaOrderItemRepository;

    public OrderItemRepositoryAdapter(JpaOrderItemRepository jpaOrderItemRepository) {
        this.jpaOrderItemRepository = jpaOrderItemRepository;
    }

    @Override
    public OrderItem save(OrderItem orderItem) {
        OrderItemEntity orderItemEntity = OrderItemConverter.toEntity(orderItem);
        OrderItemEntity savedOrderItemEntity = jpaOrderItemRepository.save(orderItemEntity);
        return OrderItemConverter.toModel(savedOrderItemEntity);
    }

    @Override
    public OrderItem update(OrderItem orderItem) {
        OrderItemEntity orderItemEntity = jpaOrderItemRepository.findById(orderItem.getId())
            .orElseThrow(() -> new OrderItemNotFoundException(orderItem.getId()));
        orderItemEntity.setPrice(orderItem.getPrice());
        orderItemEntity.setQuantity(orderItem.getQuantity());
        orderItemEntity.setSeller(SellerConverter.toEntity(orderItem.getSeller()));
        OrderItemEntity updatedOrderItemEntity = jpaOrderItemRepository.save(orderItemEntity);
        return OrderItemConverter.toModel(updatedOrderItemEntity);
    }

    @Override
    public void delete(OrderItem orderItem) {
        jpaOrderItemRepository.deleteById(orderItem.getId());
    }

    @Override
    public void deleteById(Long id) {
        jpaOrderItemRepository.deleteById(id);
    }

    @Override
    public Optional<OrderItem> findById(Long id) {
        return jpaOrderItemRepository.findById(id)
            .map(OrderItemConverter::toModel);
    }

    @Override
    public List<OrderItem> findAll() {
        return jpaOrderItemRepository.findAll()
            .stream()
            .map(OrderItemConverter::toModel)
            .toList();
    }

    @Override
    public List<OrderItem> findByOrderId(Long orderId) {
        return jpaOrderItemRepository.findAllByOrder(orderId)
            .stream()
            .map(OrderItemConverter::toModel)
            .toList();
    }

    @Override
    public List<OrderItem> findBySellerId(Long sellerId) {
        return jpaOrderItemRepository.findAllBySeller(sellerId)
            .stream()
            .map(OrderItemConverter::toModel)
            .toList();
    }

    @Override
    public List<OrderItem> findByCustomerId(Long customerId) {
        return jpaOrderItemRepository.findAllByCustomer(customerId)
            .stream()
            .map(OrderItemConverter::toModel)
            .toList();
    }
}
