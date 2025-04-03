package com.tulipan.ordersapp.sellers.application;

import com.tulipan.ordersapp.sellers.domain.model.Seller;
import com.tulipan.ordersapp.sellers.infrastructure.repository.SellerRepositoryAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class SellerServiceImplTest {

    @Mock
    private SellerRepositoryAdapter repository;

    @InjectMocks
    private SellerServiceImpl sellerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_SellerExists() {
        Seller seller = Seller.builder().id(1L).name("Seller1").build();
        when(repository.findById(anyLong())).thenReturn(Optional.of(seller));

        Optional<Seller> foundSeller = sellerService.findById(1L);

        assertTrue(foundSeller.isPresent());
        assertEquals(seller.getId(), foundSeller.get().getId());
    }

    @Test
    void findById_SellerDoesNotExist() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<Seller> foundSeller = sellerService.findById(1L);

        assertFalse(foundSeller.isPresent());
    }

    @Test
    void saveSeller_Success() {
        Seller seller = Seller.builder().id(1L).name("Seller1").build();
        when(repository.save(any(Seller.class))).thenReturn(seller);

        Seller savedSeller = sellerService.save(seller);

        assertNotNull(savedSeller);
        assertEquals(seller.getId(), savedSeller.getId());
    }

    @Test
    void deleteSeller_Success() {
        Seller seller = Seller.builder().id(1L).name("Seller1").build();
        doNothing().when(repository).delete(any(Seller.class));

        sellerService.delete(seller);

        verify(repository, times(1)).delete(any(Seller.class));
    }

    @Test
    void updateSeller_Success() {
        Seller seller = Seller.builder().id(1L).name("UpdatedSeller").build();
        when(repository.update(any(Seller.class))).thenReturn(seller);

        Seller updatedSeller = sellerService.update(seller);

        assertNotNull(updatedSeller);
        assertEquals(seller.getName(), updatedSeller.getName());
    }

    @Test
    void findAllSellers_Success() {
        List<Seller> sellers = List.of(
            Seller.builder().id(1L).name("Seller1").build(),
            Seller.builder().id(1L).name("Seller2").build()
        );
        when(repository.findAll()).thenReturn(sellers);

        List<Seller> foundSellers = sellerService.findAll();

        assertNotNull(foundSellers);
        assertEquals(2, foundSellers.size());
    }
}
