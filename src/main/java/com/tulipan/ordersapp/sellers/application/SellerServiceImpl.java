package com.tulipan.ordersapp.sellers.application;

import com.tulipan.ordersapp.sellers.domain.model.Seller;
import com.tulipan.ordersapp.sellers.domain.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {
    private final SellerRepository repository;

    @Override
    public Optional<Seller> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Seller save(Seller seller) {
        return repository.save(seller);
    }

    @Override
    public void delete(Seller seller) {
        repository.delete(seller);
    }

    @Override
    public Seller update(Seller seller) {
        return repository.update(seller);
    }

    @Override
    public List<Seller> findAll() {
        return repository.findAll();
    }
}
