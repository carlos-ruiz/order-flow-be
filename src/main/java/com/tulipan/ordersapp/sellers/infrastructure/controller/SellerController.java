package com.tulipan.ordersapp.sellers.infrastructure.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tulipan.ordersapp.sellers.domain.service.SellerService;
import com.tulipan.ordersapp.sellers.domain.SellerNotFoundException;
import com.tulipan.ordersapp.sellers.domain.model.Seller;

@RestController
@RequestMapping("/sellers")
public class SellerController {
  private SellerService sellerService;

  public SellerController(SellerService sellerService) {
    this.sellerService = sellerService;
  }

  @PostMapping
  public Seller createSeller(@RequestBody Seller seller) {
    return sellerService.save(seller);
  }

  @GetMapping("/{id}")
  public Seller getSellerById(@PathVariable Long id) {
    return sellerService.findById(id).orElseThrow(() -> new SellerNotFoundException(id));
  }

  @GetMapping
  public List<Seller> getAllSellers() {
    return sellerService.findAll();
  }

  @PutMapping("/{id}")
  public Seller updateSeller(@PathVariable Long id, @RequestBody Seller seller) {
    return sellerService.update(seller);
  }

  @DeleteMapping("/{id}")
  public void deleteSeller(@PathVariable Long id) {
    Seller seller = sellerService.findById(id).orElseThrow(() -> new SellerNotFoundException(id));
    sellerService.delete(seller);
  }
}
