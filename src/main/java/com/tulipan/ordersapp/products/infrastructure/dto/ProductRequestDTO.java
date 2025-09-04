package com.tulipan.ordersapp.products.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {
    private String name;
    private BigDecimal basePrice;
    private String color;
    private String size;
    private BigDecimal finalPrice;
    private String description;
}
