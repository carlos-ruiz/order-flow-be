package com.tulipan.ordersapp.sellers.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Seller {
    private Long id;
    private String name;
    private String lastName;
    private String address;
    private String phone;
    private String email;
    @Builder.Default
    private Boolean active = true;
}
