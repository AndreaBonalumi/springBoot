package com.example.springboot;

public enum Status {
    APPROVED("Approvato"),
    DECLINED("Rifiutato"),
    ToAPPROVE("Da approvare");

    private final String value;

    private Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
