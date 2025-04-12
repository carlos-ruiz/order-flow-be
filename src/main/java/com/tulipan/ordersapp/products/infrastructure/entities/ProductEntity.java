package com.tulipan.ordersapp.products.infrastructure.entities;

import com.tulipan.ordersapp.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;

@Builder
@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity extends BaseEntity {

    @Id
    private Long id;

    @NonNull
    private String name;

    @Column(name = "base_price")
    private BigDecimal basePrice;

    private String color;

    private String size;

    @Column(name = "final_price")
    private BigDecimal finalPrice;

    private String description;
}
