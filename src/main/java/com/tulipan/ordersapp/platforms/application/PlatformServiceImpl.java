package com.tulipan.ordersapp.platforms.application;

import com.tulipan.ordersapp.platforms.domain.model.Platform;
import com.tulipan.ordersapp.platforms.domain.repository.PlatformRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlatformServiceImpl implements PlatformService {

    private final PlatformRepository repository;

    public PlatformServiceImpl(PlatformRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Platform> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Platform save(Platform platform) {
        return repository.save(platform);
    }

    @Override
    public void delete(Platform platform) {
        repository.delete(platform);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Platform update(Platform platform) {
        return repository.update(platform);
    }

    @Override
    public List<Platform> findAll() {
        return repository.findAll();
    }

}
