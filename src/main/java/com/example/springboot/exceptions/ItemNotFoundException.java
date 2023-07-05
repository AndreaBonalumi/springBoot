package com.example.springboot.exceptions;

public class ItemNotFoundException extends Exception {
    private static final long serialVersionUID = -5630320396706560376L;
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
