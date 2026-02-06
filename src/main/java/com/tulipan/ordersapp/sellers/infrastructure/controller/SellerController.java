package com.tulipan.ordersapp.sellers.infrastructure.controller;

import com.tulipan.ordersapp.sellers.application.SellerService;
import com.tulipan.ordersapp.sellers.domain.exceptions.SellerNotFoundException;
import com.tulipan.ordersapp.sellers.domain.model.Seller;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@RequestMapping("/sellers")
public class SellerController {
    private final SellerService sellerService;

    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @PostMapping
    public ResponseEntity<Seller> createSeller(@RequestBody Seller seller) {
        log.info("Creating new seller: {}", seller);
        Seller createdSeller = sellerService.save(seller);
        return ResponseEntity.ok(createdSeller);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seller> getSellerById(@PathVariable Long id) {
        Seller seller = sellerService.findById(id).orElseThrow(() -> new SellerNotFoundException(id));
        return ResponseEntity.ok(seller);
    }

    @GetMapping
    public ResponseEntity<List<Seller>> getAllSellers() {
        List<Seller> sellers = sellerService.findAll();
        return ResponseEntity.ok(sellers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Seller> updateSeller(@PathVariable Long id, @RequestBody Seller seller) {
        seller.setId(id);
        Seller updatedSeller = sellerService.update(seller);
        return ResponseEntity.ok(updatedSeller);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeller(@PathVariable Long id) {
        Seller seller = sellerService.findById(id).orElseThrow(() -> new SellerNotFoundException(id));
        sellerService.delete(seller);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(SellerNotFoundException.class)
    public ResponseEntity<String> handleSellerNotFoundException(SellerNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
