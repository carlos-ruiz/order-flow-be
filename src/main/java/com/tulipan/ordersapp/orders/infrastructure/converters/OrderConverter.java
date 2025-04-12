package com.tulipan.ordersapp.orders.infrastructure.converters;

import com.tulipan.ordersapp.orderitems.domain.model.OrderItem;
import com.tulipan.ordersapp.orderitems.infrastructure.converters.OrderItemConverter;
import com.tulipan.ordersapp.orderitems.infrastructure.entities.OrderItemEntity;
import com.tulipan.ordersapp.orders.domain.model.Order;
import com.tulipan.ordersapp.orders.infrastructure.entities.OrderEntity;
import com.tulipan.ordersapp.platforms.infrastructure.converters.PlatformConverter;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Optional;

@UtilityClass
public class OrderConverter {
    public static Order toModel(OrderEntity orderEntity) {
        if (orderEntity == null) {
            return null;
        }
        List<OrderItem> orderItems = Optional.ofNullable(orderEntity.getOrderItems())
            .orElse(List.of())
            .stream()
            .map(OrderItemConverter::toModel)
            .toList();
        Order order = new Order();
        order.setId(orderEntity.getId());
        order.setDateTime(orderEntity.getDateTime());
        order.setDiscount(orderEntity.getDiscount());
        order.setPlatform(PlatformConverter.toModel(orderEntity.getPlatform()));
        order.setOrderItems(orderItems);
        return order;
    }

    public static OrderEntity toEntity(Order order) {
        if (order == null) {
            return null;
        }
        List<OrderItemEntity> orderItems = Optional.ofNullable(order.getOrderItems())
            .orElse(List.of())
            .stream()
            .map(OrderItemConverter::toEntity)
            .toList();
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(order.getId());
        orderEntity.setDateTime(order.getDateTime());
        orderEntity.setDiscount(order.getDiscount());
        orderEntity.setPlatform(PlatformConverter.toEntity(order.getPlatform()));
        orderEntity.setOrderItems(orderItems);
        return orderEntity;
    }


}
