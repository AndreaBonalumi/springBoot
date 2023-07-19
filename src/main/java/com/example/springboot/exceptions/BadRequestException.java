package com.example.springboot.exceptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException() { super(); }
    public BadRequestException(String messaggio) {
        super(messaggio);
    }
}
