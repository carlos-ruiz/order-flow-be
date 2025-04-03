package com.tulipan.ordersapp.sellers.infrastructure.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SellerEntityTest {

    @Test
    void sellerEntity_CreationWithBuilder() {
        SellerEntity seller = SellerEntity.builder()
            .id(1L)
            .name("John")
            .lastName("Doe")
            .address("123 Main St")
            .phone("1234567890")
            .email("john.doe@example.com")
            .build();

        assertNotNull(seller);
        assertEquals(1L, seller.getId());
        assertEquals("John", seller.getName());
        assertEquals("Doe", seller.getLastName());
        assertEquals("123 Main St", seller.getAddress());
        assertEquals("1234567890", seller.getPhone());
        assertEquals("john.doe@example.com", seller.getEmail());
    }

    @Test
    void sellerEntity_BuilderHandlesNullValues() {
        SellerEntity seller = SellerEntity.builder()
            .id(null)
            .name(null)
            .lastName(null)
            .address(null)
            .phone(null)
            .email(null)
            .build();

        assertNull(seller.getId());
        assertNull(seller.getName());
        assertNull(seller.getLastName());
        assertNull(seller.getAddress());
        assertNull(seller.getPhone());
        assertNull(seller.getEmail());
    }

    @Test
    void sellerEntity_BuilderHandlesEmptyStrings() {
        SellerEntity seller = SellerEntity.builder()
            .name("")
            .lastName("")
            .address("")
            .phone("")
            .email("")
            .build();

        assertEquals("", seller.getName());
        assertEquals("", seller.getLastName());
        assertEquals("", seller.getAddress());
        assertEquals("", seller.getPhone());
        assertEquals("", seller.getEmail());
    }

    @Test
    void sellerEntity_BuilderHandlesWhitespaceStrings() {
        SellerEntity seller = SellerEntity.builder()
            .name(" ")
            .lastName(" ")
            .address(" ")
            .phone(" ")
            .email(" ")
            .build();

        assertEquals(" ", seller.getName());
        assertEquals(" ", seller.getLastName());
        assertEquals(" ", seller.getAddress());
        assertEquals(" ", seller.getPhone());
        assertEquals(" ", seller.getEmail());
    }
}
