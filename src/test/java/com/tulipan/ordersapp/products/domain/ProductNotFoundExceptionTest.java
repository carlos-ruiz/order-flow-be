package com.tulipan.ordersapp.products.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductNotFoundExceptionTest {

    @Test
    void exceptionMessageContainsId() {
        Long id = 1L;
        ProductNotFoundException exception = new ProductNotFoundException(id);
        assertEquals("Product with id " + id + " not found.", exception.getMessage());
    }

    @Test
    void exceptionMessageContainsName() {
        String name = "TestProduct";
        ProductNotFoundException exception = new ProductNotFoundException(name);
        assertEquals("Product with name " + name + " not found.", exception.getMessage());
    }
}
