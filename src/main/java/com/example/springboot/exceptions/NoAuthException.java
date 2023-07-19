package com.example.springboot.exceptions;

public class NoAuthException extends RuntimeException{
    public NoAuthException() { super(); }
    public NoAuthException(String messaggio) {
        super(messaggio);
    }
}
