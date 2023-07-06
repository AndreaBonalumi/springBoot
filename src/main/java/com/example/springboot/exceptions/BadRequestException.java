package com.example.springboot.exceptions;

public class BadRequestException extends Exception {
    private String messaggio;
    public BadRequestException() { super(); }
    public BadRequestException(String messaggio) {
        super(messaggio);
        this.messaggio = messaggio;
    }

    public String getMessaggio() {
        return messaggio;
    }

    public void setMessaggio(String messaggio) {
        this.messaggio = messaggio;
    }
}
