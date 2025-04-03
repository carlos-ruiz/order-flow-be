package com.tulipan.ordersapp.platforms.domain.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlatformNotFoundExceptionTest {

    @Test
    void exceptionMessageContainsId() {
        PlatformNotFoundException exception = new PlatformNotFoundException(1L);
        assertEquals("Platform with id 1 not found.", exception.getMessage());
    }

    @Test
    void exceptionMessageContainsName() {
        PlatformNotFoundException exception = new PlatformNotFoundException("TestPlatform");
        assertEquals("Platform with name TestPlatform not found.", exception.getMessage());
    }
}
