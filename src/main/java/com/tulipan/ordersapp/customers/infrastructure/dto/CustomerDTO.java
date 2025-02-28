package com.tulipan.ordersapp.customers.infrastructure.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CustomerDTO {
    @NotNull
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    private String name;

    private String lastName;

    @Size(max = 255)
    private String address;

    @Size(max = 15)
    private String phone;

    @Email
    @Size(max = 100)
    private String email;

    private String note;
}
