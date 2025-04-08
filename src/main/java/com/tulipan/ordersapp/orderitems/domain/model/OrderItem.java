package com.tulipan.ordersapp.orderitems.domain.model;

import com.tulipan.ordersapp.customers.domain.model.Customer;
import com.tulipan.ordersapp.products.domain.model.Product;
import com.tulipan.ordersapp.sellers.domain.model.Seller;
import lombok.Data;

@Data
public class OrderItem {
    private Long id;
    private Integer quantity;
    private Customer customer;
    private Seller seller;
    private Product product;
    // TODO add relationship with Order
    // TODO add relationship with Status

}
