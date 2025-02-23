package com.tulipan.ordersapp.platforms.infrastructure.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tulipan.ordersapp.platforms.domain.exceptions.PlatformNotFoundException;
import com.tulipan.ordersapp.platforms.domain.model.Platform;
import com.tulipan.ordersapp.platforms.domain.service.PlatformService;

@RestController
@RequestMapping("/platforms")
public class PlatformController {
  private PlatformService platformService;

  public PlatformController(PlatformService platformService) {
    this.platformService = platformService;
  }

  @GetMapping
  public Iterable<Platform> getAllPlatforms() {
    return platformService.findAll();
  }

  @PostMapping
  public Platform createPlatform(@RequestBody Platform platform) {
    return platformService.save(platform);
  }

  @GetMapping("/{id}")
  public Platform getPlatformById(@PathVariable Long id) {
    return platformService.findById(id).orElseThrow(() -> new PlatformNotFoundException(id));
  }

  @PutMapping("/{id}")
  public Platform updatePlatform(@PathVariable Long id, @RequestBody Platform platform) {
    platform.setId(id);
    return platformService.update(platform);
  }

  @DeleteMapping("/{id}")
  public void deletePlatform(@PathVariable Long id) {
    Platform platform = platformService.findById(id).orElseThrow(() -> new PlatformNotFoundException(id));
    platformService.delete(platform);
  }

}
