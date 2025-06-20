package com.tulipan.ordersapp.status.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Status {
    private Long id;
    private String name;
    private Boolean isActive;
}
