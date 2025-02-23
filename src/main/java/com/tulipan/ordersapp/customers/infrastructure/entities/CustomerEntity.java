package com.tulipan.ordersapp.customers.infrastructure.entities;

import com.tulipan.ordersapp.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "customers")
@Data
@EqualsAndHashCode(callSuper = false)
public class CustomerEntity extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  private String lastName;

  @Column(unique = true)
  private String email;

  @Column(nullable = false, unique = true)
  private String phone;

  private String address;

  private String note;
}
