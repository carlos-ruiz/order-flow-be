package com.tulipan.ordersapp.platforms.domain.exceptions;

public class PlatformNotFoundException extends RuntimeException {
  public PlatformNotFoundException(Long id) {
    super("Platform with id " + id + " not found.");
  }

  public PlatformNotFoundException(String name) {
    super("Platform with name " + name + " not found.");
  }
}
