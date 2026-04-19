package com.tulipan.ordersapp.orderitems.infrastructure.entities;

import com.tulipan.ordersapp.orders.infrastructure.entities.OrderEntity;
import com.tulipan.ordersapp.status.infrastructure.entities.StatusEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderItemEntityTest {

    @Test
    void testToStringContainsFields() {
        OrderItemEntity item = new OrderItemEntity();
        item.setId(1L);
        item.setQuantity(2);
        item.setPrice(new BigDecimal("10.00"));
        item.setNote("Test note");
        item.setProduct("Test product");

        StatusEntity status = new StatusEntity();
        status.setName("NEW");
        item.setStatus(status);

        OrderEntity order = new OrderEntity();
        order.setId(100L);
        item.setOrder(order);

        String result = item.toString();
        assertTrue(result.contains("id=1"));
        assertTrue(result.contains("quantity=2"));
        assertTrue(result.contains("price=10.00"));
        assertTrue(result.contains("note='Test note'"));
        assertTrue(result.contains("product='Test product'"));
        assertTrue(result.contains("status=NEW"));
        assertTrue(result.contains("orderId=100"));
    }

    @Test
    void testLombokGeneratedMethods() {
        OrderItemEntity item1 = new OrderItemEntity();
        item1.setId(1L);
        item1.setQuantity(2);

        OrderItemEntity item2 = new OrderItemEntity();
        item2.setId(1L);
        item2.setQuantity(2);

        assertEquals(item1, item2);
        assertEquals(item1.hashCode(), item2.hashCode());
        item2.setQuantity(3);
        assertNotEquals(item1, item2);
    }
}
