package com.tulipan.ordersapp.sellers.domain.model;

import lombok.Data;

@Data
public class Seller {
    private Long id;
    private String name;
    private String lastName;
    private String address;
    private String phone;
    private String email;
}
