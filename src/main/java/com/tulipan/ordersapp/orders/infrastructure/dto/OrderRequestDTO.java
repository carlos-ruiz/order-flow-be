package com.tulipan.ordersapp.orders.infrastructure.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class OrderRequestDTO {
    private LocalDateTime dateTime;
    private BigDecimal discount;
    private Long platformId;
}
