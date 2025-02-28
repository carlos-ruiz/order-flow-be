package com.tulipan.ordersapp.customers.domain.model;

import lombok.Data;

@Data
public class Customer {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String note;
}
