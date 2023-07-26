package com.example.springboot.exceptions;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ErrorResponse {
    private int code;
    private String message;
    private Map<String, String> errorMap = new HashMap<>();
}
