package com.tulipan.ordersapp.orders.infrastructure.converters;

import com.tulipan.ordersapp.orders.domain.model.Order;
import com.tulipan.ordersapp.orders.infrastructure.entities.OrderEntity;
import com.tulipan.ordersapp.platforms.infrastructure.converters.PlatformConverter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OrderConverter {
    public static Order toModel(OrderEntity orderEntity) {
        if (orderEntity == null) {
            return null;
        }
        Order order = new Order();
        order.setId(orderEntity.getId());
        order.setDateTime(orderEntity.getDateTime());
        order.setDiscount(orderEntity.getDiscount());
        order.setPlatform(PlatformConverter.toModel(orderEntity.getPlatform()));
        return order;
    }

    public static OrderEntity toEntity(Order order) {
        if (order == null) {
            return null;
        }
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(order.getId());
        orderEntity.setDateTime(order.getDateTime());
        orderEntity.setDiscount(order.getDiscount());
        orderEntity.setPlatform(PlatformConverter.toEntity(order.getPlatform()));
        return orderEntity;
    }


}
