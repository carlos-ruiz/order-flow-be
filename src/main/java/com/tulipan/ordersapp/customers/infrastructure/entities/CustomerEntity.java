package com.tulipan.ordersapp.customers.infrastructure.entities;

import com.tulipan.ordersapp.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Table(name = "customers")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;

    @PrePersist
    public void prePersist() {
        if (active == null) {
            active = true;
        }
    }
}
