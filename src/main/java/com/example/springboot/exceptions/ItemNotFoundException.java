package com.example.springboot.exceptions;

public class ItemNotFoundException extends Exception {
    private String messaggio;

    public ItemNotFoundException() {
        super();
    }
    public ItemNotFoundException(String message) {
        super(message);
        this.messaggio = message;
    }

    public String getMessaggio() {
        return messaggio;
    }

    public void setMessaggio(String messaggio) {
        this.messaggio = messaggio;
    }
}
