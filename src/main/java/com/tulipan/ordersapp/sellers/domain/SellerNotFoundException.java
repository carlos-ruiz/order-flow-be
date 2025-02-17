package com.tulipan.ordersapp.sellers.domain;

public class SellerNotFoundException extends RuntimeException {
  public SellerNotFoundException(Long id) {
    super("Seller with id " + id + " not found.");
  }
}
