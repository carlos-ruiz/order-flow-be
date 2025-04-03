package com.tulipan.ordersapp.platforms.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Platform {
    private Long id;
    private String name;
    private Float customerFee;
    private Float sellerCommission;
    private Boolean active;

}
