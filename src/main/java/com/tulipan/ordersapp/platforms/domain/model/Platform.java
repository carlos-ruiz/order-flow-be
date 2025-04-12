package com.tulipan.ordersapp.platforms.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Platform {
    private Long id;
    private String name;
    private BigDecimal customerFee;
    private BigDecimal sellerCommission;
    private Boolean active;

}
