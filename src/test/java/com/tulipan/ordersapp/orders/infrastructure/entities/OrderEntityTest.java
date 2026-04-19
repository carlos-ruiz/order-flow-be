package com.tulipan.ordersapp.orders.infrastructure.entities;

import com.tulipan.ordersapp.orderitems.infrastructure.entities.OrderItemEntity;
import com.tulipan.ordersapp.platforms.infrastructure.entities.PlatformEntity;
import com.tulipan.ordersapp.status.infrastructure.entities.StatusEntity;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderEntityTest {

    @Test
    void toString_AllNullsAndEmptyOrderItems() {
        OrderEntity order = new OrderEntity();
        order.setOrderItems(Collections.emptyList());
        String result = order.toString();
        assertTrue(result.contains("platform=null"));
        assertTrue(result.contains("status=null"));
        assertTrue(result.contains("orderItems=[]"));
    }

    @Test
    void toString_PlatformAndStatusNotNull_OrderItemsEmpty() {
        PlatformEntity platform = new PlatformEntity();
        platform.setName("TestPlatform");
        StatusEntity status = new StatusEntity();
        status.setName("TestStatus");

        OrderEntity order = new OrderEntity();
        order.setPlatform(platform);
        order.setStatus(status);
        order.setOrderItems(Collections.emptyList());

        String result = order.toString();
        assertTrue(result.contains("platform=TestPlatform"));
        assertTrue(result.contains("status=TestStatus"));
        assertTrue(result.contains("orderItems=[]"));
    }

    @Test
    void toString_PlatformAndStatusNotNull_OrderItemsNotEmpty() {
        PlatformEntity platform = new PlatformEntity();
        platform.setName("TestPlatform");
        StatusEntity status = new StatusEntity();
        status.setName("TestStatus");

        OrderItemEntity item = new OrderItemEntity();
        OrderEntity order = new OrderEntity();
        order.setPlatform(platform);
        order.setStatus(status);
        order.setOrderItems(Collections.singletonList(item));

        String result = order.toString();
        assertTrue(result.contains("platform=TestPlatform"));
        assertTrue(result.contains("status=TestStatus"));
        assertTrue(result.contains("orderItems=["));
    }
}
