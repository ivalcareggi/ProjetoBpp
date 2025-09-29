package com.project.fgh.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    // Construtor que recebe a mensagem de erro
    public ResourceNotFoundException(String message) {
        super(message);
    }
}