package com.tulipan.ordersapp.platforms.infrastructure.entities;

import com.tulipan.ordersapp.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "platforms")
@Data
@EqualsAndHashCode(callSuper = false)
public class PlatformEntity extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  private Float customerFee;

  private Float sellerCommission;

  @Column(nullable = false)
  private Boolean active = true;

  @PrePersist
  public void prePersist() {
    if (active == null) {
      active = true;
    }
  }
}
