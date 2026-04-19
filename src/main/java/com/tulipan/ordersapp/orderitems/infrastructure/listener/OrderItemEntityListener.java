package com.tulipan.ordersapp.orderitems.infrastructure.listener;

import com.tulipan.ordersapp.orderitems.infrastructure.entities.OrderItemEntity;
import com.tulipan.ordersapp.orders.infrastructure.entities.OrderEntity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class OrderItemEntityListener {

    private static final Logger log = LoggerFactory.getLogger(OrderItemEntityListener.class);

    @PrePersist
    @PreUpdate
    @PreRemove
    public void recalculateOrderTotalAmount(OrderItemEntity orderItemEntity) {
        OrderEntity order = orderItemEntity.getOrder();
        BigDecimal totalAmount = BigDecimal.ZERO;
        if (order != null) {
            totalAmount = order.getOrderItems().stream().reduce(
                totalAmount,
                (total, item) -> total.add(item.getPrice().multiply(
                    new java.math.BigDecimal(item.getQuantity())
                )),
                java.math.BigDecimal::add
            );
            log.info("Total amount for order {} is {}", order.getId(), totalAmount);
            order.setTotalAmount(totalAmount);
        }
    }
}
