package com.tulipan.ordersapp.status.infrastructure.controller;

import com.tulipan.ordersapp.status.application.StatusService;
import com.tulipan.ordersapp.status.domain.exceptions.StatusNotFoundException;
import com.tulipan.ordersapp.status.domain.model.Status;
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
@RequestMapping("/status")
public class StatusController {

    private final StatusService statusService;

    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping
    public ResponseEntity<List<Status>> getAllStatuses() {
        List<Status> statuses = statusService.findAll();
        return ResponseEntity.ok(statuses);
    }

    @GetMapping("/active")
    public ResponseEntity<List<Status>> getAllActiveStatuses() {
        List<Status> statuses = statusService.findAllByIsActive(true).stream()
            .map(status -> new Status(status.getId(), status.getName(), status.getIsActive()))
            .toList();
        return ResponseEntity.ok(statuses);
    }

    @GetMapping("/inactive")
    public ResponseEntity<List<Status>> getAllInactiveStatuses() {
        List<Status> statuses = statusService.findAllByIsActive(false).stream()
            .map(status -> new Status(status.getId(), status.getName(), status.getIsActive()))
            .toList();
        return ResponseEntity.ok(statuses);
    }

    @PostMapping
    public ResponseEntity<Status> createStatus(@RequestBody Status status) {
        Status createdStatus = statusService.save(status);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStatus);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Status> getStatusById(@PathVariable Long id) {
        Status status = statusService.findById(id).orElseThrow(() -> new StatusNotFoundException(id));
        return ResponseEntity.ok(status);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Status> getStatusByName(@PathVariable String name) {
        Status status = statusService.findByName(name).orElseThrow(() -> new StatusNotFoundException(name));
        return ResponseEntity.ok(status);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Status> updateStatus(@PathVariable Long id, @RequestBody Status status) {
        status.setId(id);
        Status updatedStatus = statusService.update(status);
        return ResponseEntity.ok(updatedStatus);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStatus(@PathVariable Long id) {
        if (!statusService.existsById(id)) {
            throw new StatusNotFoundException(id);
        }
        statusService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler
    public ResponseEntity<String> handleStatusNotFoundException(StatusNotFoundException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }
}
