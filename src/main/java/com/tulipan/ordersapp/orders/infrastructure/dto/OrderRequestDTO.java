package com.tulipan.ordersapp.orders.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {
    private LocalDateTime dateTime;
    private BigDecimal discount;
    private Long platformId;
    private Long statusId;
    private BigDecimal totalAmount;
}
