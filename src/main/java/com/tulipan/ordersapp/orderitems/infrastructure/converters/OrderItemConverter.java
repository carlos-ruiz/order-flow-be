package com.tulipan.ordersapp.orderitems.infrastructure.converters;

import com.tulipan.ordersapp.customers.infrastructure.converters.CustomerConverter;
import com.tulipan.ordersapp.orderitems.domain.model.OrderItem;
import com.tulipan.ordersapp.orderitems.infrastructure.dto.OrderItemRequestDTO;
import com.tulipan.ordersapp.orderitems.infrastructure.entities.OrderItemEntity;
import com.tulipan.ordersapp.orders.application.OrderService;
import com.tulipan.ordersapp.orders.domain.model.Order;
import com.tulipan.ordersapp.orders.infrastructure.converters.OrderConverter;
import com.tulipan.ordersapp.products.infrastructure.converters.ProductConverter;
import com.tulipan.ordersapp.sellers.infrastructure.converters.SellerConverter;
import com.tulipan.ordersapp.status.infrastructure.converters.StatusConverter;
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
        orderItem.setStatus(StatusConverter.toModel(orderItemEntity.getStatus()));
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
        orderItemEntity.setStatus(StatusConverter.toEntity(orderItem.getStatus()));
        return orderItemEntity;
    }

    public static OrderItem dtoToModel(OrderItemRequestDTO orderItemRequestDTO, OrderService orderService) {
        OrderItem orderItem = OrderItem.builder()
            .quantity(orderItemRequestDTO.getQuantity())
            .price(orderItemRequestDTO.getPrice())
//                .order(OrderConverter.toModel(orderItemRequestDTO.getOrder()))
//                .product(ProductConverter.toModel(orderItemRequestDTO.getProduct()))
//                .customer(CustomerConverter.toModel(orderItemRequestDTO.getCustomer()))
//                .seller(SellerConverter.toModel(orderItemRequestDTO.getSeller()))
            .build();
        Order order = orderService.findById(orderItemRequestDTO.getOrderId()).orElse(null);
        if (order != null) {
            orderItem.setOrder(order);
        }
        return orderItem;
    }
}
