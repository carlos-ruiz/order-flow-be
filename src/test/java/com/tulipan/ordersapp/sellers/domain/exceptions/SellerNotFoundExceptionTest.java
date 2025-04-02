package com.tulipan.ordersapp.sellers.domain.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SellerNotFoundExceptionTest {

    @Test
    void sellerNotFoundException_MessageContainsId() {
        Long id = 1L;
        SellerNotFoundException exception = new SellerNotFoundException(id);

        assertEquals("Seller with id " + id + " not found.", exception.getMessage());
    }

    @Test
    void sellerNotFoundException_NullId() {
        Long id = null;
        SellerNotFoundException exception = new SellerNotFoundException(id);

        assertEquals("Seller with id null not found.", exception.getMessage());
    }
}
