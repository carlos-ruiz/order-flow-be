package com.tulipan.ordersapp.orderitems.infrastructure.converters;

import com.tulipan.ordersapp.customers.infrastructure.converters.CustomerConverter;
import com.tulipan.ordersapp.orderitems.domain.model.OrderItem;
import com.tulipan.ordersapp.orderitems.infrastructure.entities.OrderItemEntity;
import com.tulipan.ordersapp.orders.infrastructure.converters.OrderConverter;
import com.tulipan.ordersapp.products.infrastructure.converters.ProductConverter;
import com.tulipan.ordersapp.sellers.infrastructure.converters.SellerConverter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OrderItemConverter {

    public static OrderItem toModel(OrderItemEntity orderItemEntity) {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(orderItemEntity.getId());
        orderItem.setPrice(orderItemEntity.getPrice());
        orderItem.setQuantity(orderItemEntity.getQuantity());
        orderItem.setSeller(SellerConverter.toModel(orderItemEntity.getSeller()));
        orderItem.setCustomer(CustomerConverter.toModel(orderItemEntity.getCustomer()));
        orderItem.setProduct(ProductConverter.toModel(orderItemEntity.getProduct()));
        orderItem.setOrder(OrderConverter.toModel(orderItemEntity.getOrder()));
        return orderItem;
    }

    public static OrderItemEntity toEntity(OrderItem orderItem) {
        OrderItemEntity orderItemEntity = new OrderItemEntity();
        orderItemEntity.setId(orderItem.getId());
        orderItemEntity.setPrice(orderItem.getPrice());
        orderItemEntity.setQuantity(orderItem.getQuantity());
        orderItemEntity.setSeller(SellerConverter.toEntity(orderItem.getSeller()));
        orderItemEntity.setCustomer(CustomerConverter.toEntity(orderItem.getCustomer()));
        orderItemEntity.setProduct(ProductConverter.toEntity(orderItem.getProduct()));
        orderItemEntity.setOrder(OrderConverter.toEntity(orderItem.getOrder()));
        return orderItemEntity;
    }
}
