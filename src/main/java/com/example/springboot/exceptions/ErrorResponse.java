package com.example.springboot.exceptions;

import lombok.Data;

@Data
public class ErrorResponse {
    private int code;
    private String message;
}
