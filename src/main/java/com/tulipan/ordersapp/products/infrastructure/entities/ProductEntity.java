package com.tulipan.ordersapp.products.infrastructure.entities;

import com.tulipan.ordersapp.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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

    private String name;

    private BigDecimal basePrice;

    private String color;

    private String size;

    private BigDecimal finalPrice;
}
