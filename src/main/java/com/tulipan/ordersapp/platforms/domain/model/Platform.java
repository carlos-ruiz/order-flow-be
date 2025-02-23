package com.tulipan.ordersapp.platforms.domain.model;

import lombok.Data;

@Data
public class Platform {
  private Long id;
  private String name;
  private Float customerFee;
  private Float sellerCommission;
  private Boolean active;

}
