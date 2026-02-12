package com.tulipan.ordersapp.orders.infrastructure.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tulipan.ordersapp.orderitems.infrastructure.entities.OrderItemEntity;
import com.tulipan.ordersapp.platforms.infrastructure.entities.PlatformEntity;
import com.tulipan.ordersapp.status.infrastructure.entities.StatusEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Tolerate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateTime;

    private BigDecimal discount;

    @ManyToOne
    @JoinColumn(name = "platform_id", nullable = false)
    private PlatformEntity platform;

    private BigDecimal totalAmount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @Builder.Default
    private List<OrderItemEntity> orderItems = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private StatusEntity status;

    @Tolerate
    public OrderEntity(Long id, LocalDateTime dateTime, BigDecimal discount, PlatformEntity platform, BigDecimal totalAmount) {
        this.id = id;
        this.dateTime = dateTime;
        this.discount = discount;
        this.platform = platform;
        this.totalAmount = totalAmount;
        this.orderItems = new ArrayList<>();
    }
}
