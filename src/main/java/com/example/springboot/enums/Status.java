package com.example.springboot.enums;

public enum Status {
    APPROVED("Approvato"),
    DECLINED("Rifiutato"),
    TOAPPROVE("Da approvare");

    private final String value;

    private Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
