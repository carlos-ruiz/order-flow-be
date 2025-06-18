package com.tulipan.ordersapp.orderitems.domain.exceptions;

public class OrderItemNotFoundException extends RuntimeException {
    public OrderItemNotFoundException(Long id) {
        super("Order item with id " + id + " not found.");
    }
}
