package com.tulipan.ordersapp.orderitems.domain.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderItemNotFoundExceptionTest {

    @Test
    void throwsExceptionWithCorrectMessageWhenIdIsProvided() {
        Long id = 123L;
        OrderItemNotFoundException exception = new OrderItemNotFoundException(id);
        assertEquals("Order item with id 123 not found.", exception.getMessage());
    }

    @Test
    void throwsExceptionWithCorrectMessageWhenIdIsNull() {
        Long id = null;
        OrderItemNotFoundException exception = new OrderItemNotFoundException(id);
        assertEquals("Order item with id null not found.", exception.getMessage());
    }
}
