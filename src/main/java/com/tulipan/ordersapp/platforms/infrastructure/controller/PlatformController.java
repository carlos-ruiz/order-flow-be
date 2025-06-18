package com.tulipan.ordersapp.platforms.infrastructure.controller;

import com.tulipan.ordersapp.platforms.application.PlatformService;
import com.tulipan.ordersapp.platforms.domain.exceptions.PlatformNotFoundException;
import com.tulipan.ordersapp.platforms.domain.model.Platform;
import com.tulipan.ordersapp.platforms.infrastructure.dto.PlatformDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/platforms")
public class PlatformController {
    private final PlatformService platformService;

    public PlatformController(PlatformService platformService) {
        this.platformService = platformService;
    }

    @GetMapping
    public ResponseEntity<List<PlatformDTO>> getAllPlatforms() {
        List<PlatformDTO> platforms = platformService.findAll().stream()
            .map(this::toDTO)
            .toList();
        return ResponseEntity.ok(platforms);
    }

    @PostMapping
    public ResponseEntity<PlatformDTO> createPlatform(@RequestBody PlatformDTO platformDTO) {
        Platform platform = toModel(platformDTO);
        Platform createdPlatform = platformService.save(platform);
        PlatformDTO createdPlatformDTO = toDTO(createdPlatform);
        return new ResponseEntity<>(createdPlatformDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlatformDTO> getPlatformById(@PathVariable Long id) {
        Platform platform = platformService.findById(id)
            .orElseThrow(() -> new PlatformNotFoundException(id));
        PlatformDTO platformDTO = toDTO(platform);
        return new ResponseEntity<>(platformDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlatformDTO> updatePlatform(@PathVariable Long id, @RequestBody PlatformDTO platformDTO) {
        Platform platform = toModel(platformDTO);
        platform.setId(id);
        Platform updatedPlatform = platformService.update(platform);
        PlatformDTO updatedPlatformDTO = toDTO(updatedPlatform);
        return new ResponseEntity<>(updatedPlatformDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlatform(@PathVariable Long id) {
        Platform platform = platformService.findById(id)
            .orElseThrow(() -> new PlatformNotFoundException(id));
        platformService.delete(platform);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(PlatformNotFoundException.class)
    public ResponseEntity<String> handlePlatformNotFoundException(PlatformNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    private PlatformDTO toDTO(Platform platform) {
        return PlatformDTO.builder()
            .id(platform.getId())
            .name(platform.getName())
            .active(platform.getActive())
            .customerFee(platform.getCustomerFee())
            .sellerCommission(platform.getSellerCommission())
            .build();
    }

    private Platform toModel(PlatformDTO platformDTO) {
        return Platform.builder()
            .id(platformDTO.getId())
            .name(platformDTO.getName())
            .active(platformDTO.getActive())
            .customerFee(platformDTO.getCustomerFee())
            .sellerCommission(platformDTO.getSellerCommission())
            .build();
    }
}
