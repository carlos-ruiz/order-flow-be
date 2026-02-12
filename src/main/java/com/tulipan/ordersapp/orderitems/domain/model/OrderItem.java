package com.tulipan.ordersapp.orderitems.domain.model;

import com.tulipan.ordersapp.customers.domain.model.Customer;
import com.tulipan.ordersapp.orders.domain.model.Order;
import com.tulipan.ordersapp.sellers.domain.model.Seller;
import com.tulipan.ordersapp.status.domain.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class OrderItem {
    private Long id;
    private Integer quantity;
    private Customer customer;
    private Seller seller;
    private String product;
    private BigDecimal price;
    private BigDecimal tax;
    private Order order;
    private Status status;
}
