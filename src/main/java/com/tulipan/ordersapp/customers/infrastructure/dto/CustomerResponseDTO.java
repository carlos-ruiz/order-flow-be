package com.tulipan.ordersapp.customers.infrastructure.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerResponseDTO {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String note;
}
