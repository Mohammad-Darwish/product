package com.gfsp.product.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    private UUID id;

    public ResourceNotFoundException(UUID id) {
        super(String.format("Product with ID: %s not found", id));
        this.id = id;
    }
}
