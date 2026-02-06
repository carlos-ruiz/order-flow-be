package com.tulipan.ordersapp.sellers.infrastructure.repository;

import com.tulipan.ordersapp.customers.domain.exceptions.CustomerNotFoundException;
import com.tulipan.ordersapp.sellers.domain.model.Seller;
import com.tulipan.ordersapp.sellers.domain.repository.SellerRepository;
import com.tulipan.ordersapp.sellers.infrastructure.converters.SellerConverter;
import com.tulipan.ordersapp.sellers.infrastructure.entities.SellerEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SellerRepositoryAdapter implements SellerRepository {
    private final JpaSellerRepository jpaSellerRepository;

    public SellerRepositoryAdapter(JpaSellerRepository jpaSellerRepository) {
        this.jpaSellerRepository = jpaSellerRepository;
    }

    public Seller save(Seller seller) {
        SellerEntity entity = SellerConverter.toEntity(seller);
        SellerEntity savedEntity = jpaSellerRepository.save(entity);
        return SellerConverter.toModel(savedEntity);
    }

    public Optional<Seller> findById(Long id) {
        return jpaSellerRepository.findById(id)
            .map(SellerConverter::toModel);
    }

    public List<Seller> findAll() {
        return jpaSellerRepository.findAll()
            .stream()
            .map(SellerConverter::toModel)
            .toList();
    }

    public void deleteById(Long id) {
        jpaSellerRepository.deleteById(id);
    }

    public void delete(Seller seller) {
        SellerEntity entity = SellerConverter.toEntity(seller);
        jpaSellerRepository.delete(entity);
    }

    public Seller update(Seller seller) {
        SellerEntity entity = jpaSellerRepository.findById(seller.getId())
            .orElseThrow(() -> new CustomerNotFoundException(seller.getId()));
        entity.setName(seller.getName());
        entity.setLastName(seller.getLastName());
        entity.setAddress(seller.getAddress());
        entity.setPhone(seller.getPhone());
        entity.setEmail(seller.getEmail());
        entity.setActive(seller.getActive());
        SellerEntity updatedEntity = jpaSellerRepository.save(entity);
        return SellerConverter.toModel(updatedEntity);
    }
}
