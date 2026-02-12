package com.tulipan.ordersapp.orders.domain.model;

import com.tulipan.ordersapp.orderitems.domain.model.OrderItem;
import com.tulipan.ordersapp.platforms.domain.model.Platform;
import com.tulipan.ordersapp.status.domain.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Tolerate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    private Long id;
    private LocalDateTime dateTime;
    private BigDecimal discount;
    private Platform platform;
    @Builder.Default
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private Status status;
    @Builder.Default
    private List<OrderItem> orderItems = Collections.emptyList();

    @Tolerate
    public Order(Long id, LocalDateTime dateTime, BigDecimal discount, Platform platform, BigDecimal totalAmount) {
        this.id = id;
        this.dateTime = dateTime;
        this.discount = discount;
        this.platform = platform;
        this.totalAmount = totalAmount;
        this.orderItems = Collections.emptyList();
    }

    @Tolerate
    public Order(Long id, LocalDateTime dateTime, BigDecimal discount, Platform platform, BigDecimal totalAmount, Status status) {
        this.id = id;
        this.dateTime = dateTime;
        this.discount = discount;
        this.platform = platform;
        this.totalAmount = totalAmount;
        this.status = status;
        this.orderItems = Collections.emptyList();
    }
}
