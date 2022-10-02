package com.example.springboot_assignment_free.exceptions;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}
