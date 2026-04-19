package com.tulipan.ordersapp.orders.infrastructure.dto;

import com.tulipan.ordersapp.orderitems.domain.model.OrderItem;
import com.tulipan.ordersapp.orders.domain.model.Order;
import com.tulipan.ordersapp.platforms.domain.model.Platform;
import com.tulipan.ordersapp.status.domain.model.Status;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrderResponseDTOTest {

    @Test
    void fromOrder_AllNulls() {
        Order order = mock(Order.class);
        when(order.getId()).thenReturn(null);
        when(order.getDateTime()).thenReturn(null);
        when(order.getDiscount()).thenReturn(null);
        when(order.getPlatform()).thenReturn(null);
        when(order.getTotalAmount()).thenReturn(null);
        when(order.getStatus()).thenReturn(null);
        when(order.getOrderItems()).thenReturn(null);

        OrderResponseDTO dto = OrderResponseDTO.fromOrder(order);

        assertNull(dto.getPlatformId());
        assertNull(dto.getPlatformName());
        assertNull(dto.getStatusId());
        assertNull(dto.getStatusName());
        assertNotNull(dto.getItems());
        assertTrue(dto.getItems().isEmpty());
    }

    @Test
    void fromOrder_PlatformAndStatusNotNull_OrderItemsNull() {
        Order order = mock(Order.class);

        Platform platform = new Platform();
        platform.setId(1L);
        platform.setName("PlatformName");

        Status status = new Status();
        status.setId(2L);
        status.setName("StatusName");

        when(order.getPlatform()).thenReturn(platform);
        when(order.getStatus()).thenReturn(status);
        when(order.getOrderItems()).thenReturn(null);

        OrderResponseDTO dto = OrderResponseDTO.fromOrder(order);

        assertEquals(1L, dto.getPlatformId());
        assertEquals("PlatformName", dto.getPlatformName());
        assertEquals(2L, dto.getStatusId());
        assertEquals("StatusName", dto.getStatusName());
        assertNotNull(dto.getItems());
        assertTrue(dto.getItems().isEmpty());
    }

    @Test
    void fromOrder_OrderItemsEmpty() {
        Order order = mock(Order.class);

        when(order.getOrderItems()).thenReturn(List.of());

        OrderResponseDTO dto = OrderResponseDTO.fromOrder(order);

        assertNotNull(dto.getItems());
        assertTrue(dto.getItems().isEmpty());
    }

    @Test
    void fromOrder_OrderItemsNotEmpty() {
        Order order = mock(Order.class);
        OrderItem orderItem = mock(OrderItem.class);

        when(order.getOrderItems()).thenReturn(List.of(orderItem));

        OrderResponseDTO dto = OrderResponseDTO.fromOrder(order);

        assertNotNull(dto.getItems());
        assertEquals(1, dto.getItems().size());
    }
}
