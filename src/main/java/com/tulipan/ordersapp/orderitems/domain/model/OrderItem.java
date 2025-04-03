package com.tulipan.ordersapp.orderitems.domain.model;

import com.tulipan.ordersapp.customers.domain.model.Customer;
import com.tulipan.ordersapp.sellers.domain.model.Seller;
import lombok.Data;

@Data
public class OrderItem {
    private Long id;
    private String productName;
    private Float productPrice; // This is the base price, shown in platform
    private String productColor;
    private String productSize;
    private Float productFinalPrice; // This is the price after tax or discount
    private Integer quantity;
    private Customer customer;
    private Seller seller;
    // TODO add relationship with Order
    // TODO add relationship with Status

}
