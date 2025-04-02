package com.tulipan.ordersapp.sellers.infrastructure.repository;

import com.tulipan.ordersapp.customers.domain.exceptions.CustomerNotFoundException;
import com.tulipan.ordersapp.sellers.domain.model.Seller;
import com.tulipan.ordersapp.sellers.infrastructure.entities.SellerEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SellerRepositoryAdapter {
    private final SellerRepository sellerRepository;

    public SellerRepositoryAdapter(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    private SellerEntity toEntity(Seller seller) {
        SellerEntity entity = SellerEntity.builder().build();
        entity.setId(seller.getId());
        entity.setName(seller.getName());
        entity.setLastName(seller.getLastName());
        entity.setAddress(seller.getAddress());
        entity.setPhone(seller.getPhone());
        entity.setEmail(seller.getEmail());
        return entity;
    }

    private Seller toModel(SellerEntity entity) {
        Seller seller = Seller.builder().build();
        seller.setId(entity.getId());
        seller.setName(entity.getName());
        seller.setLastName(entity.getLastName());
        seller.setAddress(entity.getAddress());
        seller.setPhone(entity.getPhone());
        seller.setEmail(entity.getEmail());
        return seller;
    }

    public Seller save(Seller seller) {
        SellerEntity entity = toEntity(seller);
        SellerEntity savedEntity = sellerRepository.save(entity);
        return toModel(savedEntity);
    }

    public Optional<Seller> findById(Long id) {
        return sellerRepository.findById(id)
            .map(this::toModel);
    }

    public List<Seller> findAll() {
        return sellerRepository.findAll()
            .stream()
            .map(this::toModel)
            .toList();
    }

    public void deleteById(Long id) {
        sellerRepository.deleteById(id);
    }

    public void delete(Seller seller) {
        SellerEntity entity = toEntity(seller);
        sellerRepository.delete(entity);
    }

    public Seller update(Seller seller) {
        SellerEntity entity = sellerRepository.findById(seller.getId())
            .orElseThrow(() -> new CustomerNotFoundException(seller.getId()));
        entity.setName(seller.getName());
        entity.setLastName(seller.getLastName());
        entity.setAddress(seller.getAddress());
        entity.setPhone(seller.getPhone());
        entity.setEmail(seller.getEmail());
        SellerEntity updatedEntity = sellerRepository.save(entity);
        return toModel(updatedEntity);
    }
}
