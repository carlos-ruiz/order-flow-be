package com.tulipan.ordersapp.customers.infrastructure.dto;

import lombok.Data;

@Data
public class CustomerRequestDTO {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String note;
    private Boolean active;
}
