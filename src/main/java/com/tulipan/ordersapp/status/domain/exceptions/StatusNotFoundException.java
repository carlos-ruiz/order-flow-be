package com.tulipan.ordersapp.status.domain.exceptions;

public class StatusNotFoundException extends RuntimeException {
    public StatusNotFoundException(Long id) {
        super("Status with id " + id + " not found");
    }

    public StatusNotFoundException(String name) {
        super("Status with name " + name + " not found");
    }
}
