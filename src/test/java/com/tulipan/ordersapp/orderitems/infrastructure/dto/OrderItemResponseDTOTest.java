package com.tulipan.ordersapp.orderitems.infrastructure.dto;

import com.tulipan.ordersapp.customers.domain.model.Customer;
import com.tulipan.ordersapp.orderitems.domain.model.OrderItem;
import com.tulipan.ordersapp.orders.domain.model.Order;
import com.tulipan.ordersapp.sellers.domain.model.Seller;
import com.tulipan.ordersapp.status.domain.model.Status;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class OrderItemResponseDTOTest {

    @Test
    void fromOrderItem() {
        Customer customer = Customer.builder()
            .id(1L)
            .name("Test Customer")
            .lastName("Customer Last Name")
            .email("mail@mail.com")
            .phone("1234567890")
            .address("Customer address")
            .note("Customer note")
            .active(true)
            .build();
        Seller seller = Seller.builder()
            .id(1L)
            .name("Test Seller")
            .lastName("Seller Last Name")
            .address("Seller address")
            .phone("0987654321")
            .email("mailseller@mail.com")
            .active(true)
            .build();

        Order order = Order.builder()
            .id(1L)
            .dateTime(null)
            .discount(BigDecimal.ZERO)
            .platform(null)
            .totalAmount(BigDecimal.ZERO)
            .status(null)
            .build();
        Status status = Status.builder()
            .id(1L)
            .name("Processing")
            .build();
        OrderItem orderItem = OrderItem.builder()
            .id(1L)
            .quantity(5)
            .customer(customer)
            .seller(seller)
            .product("Test Product")
            .price(BigDecimal.valueOf(100.00))
            .tax(BigDecimal.valueOf(20.00))
            .order(order)
            .status(status)
            .build();

        OrderItemResponseDTO dto = OrderItemResponseDTO.fromOrderItem(orderItem);
        assertEquals(order.getId(), dto.getOrderId());
        assertEquals(orderItem.getId(), dto.getId());
        assertEquals(orderItem.getQuantity(), dto.getQuantity());
        assertEquals(customer.getId(), dto.getCustomerId());
        assertEquals(customer.getName(), dto.getCustomerName());
        assertEquals(seller.getId(), dto.getSellerId());
        assertEquals(seller.getName(), dto.getSellerName());
        assertEquals(orderItem.getProduct(), dto.getProduct());
        assertEquals(orderItem.getPrice(), dto.getPrice());
        assertEquals(orderItem.getTax(), dto.getTax());
    }

    @Test
    void fromOrderItemWithRelationsNUll() {
        OrderItem orderItem = OrderItem.builder()
            .id(1L)
            .quantity(5)
            .product("Test Product")
            .price(BigDecimal.valueOf(100.00))
            .tax(BigDecimal.valueOf(20.00))
            .build();

        OrderItemResponseDTO dto = OrderItemResponseDTO.fromOrderItem(orderItem);
        assertNull(dto.getOrderId());
        assertNull(dto.getStatusId());
        assertEquals(orderItem.getId(), dto.getId());
        assertEquals(orderItem.getQuantity(), dto.getQuantity());
        assertNull(dto.getCustomerId());
        assertNull(dto.getCustomerName());
        assertNull(dto.getSellerId());
        assertNull(dto.getSellerName());
        assertEquals(orderItem.getProduct(), dto.getProduct());
        assertEquals(orderItem.getPrice(), dto.getPrice());
        assertEquals(orderItem.getTax(), dto.getTax());
    }
}
