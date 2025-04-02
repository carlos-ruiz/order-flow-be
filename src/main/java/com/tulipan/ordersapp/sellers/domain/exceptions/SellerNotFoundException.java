package com.tulipan.ordersapp.sellers.domain.exceptions;

public class SellerNotFoundException extends RuntimeException {
    public SellerNotFoundException(Long id) {
        super("Seller with id " + id + " not found.");
    }

    public SellerNotFoundException(String name) {
        super("Seller with name " + name + " not found.");
    }
}
