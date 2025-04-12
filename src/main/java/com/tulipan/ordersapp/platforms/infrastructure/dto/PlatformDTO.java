package com.tulipan.ordersapp.platforms.infrastructure.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class PlatformDTO {
    @NotNull
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    private String name;

    private BigDecimal customerFee;

    private BigDecimal sellerCommission;

    @NotNull
    private Boolean active;
}
