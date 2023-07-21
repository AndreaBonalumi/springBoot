package com.example.springboot.exceptions;

import lombok.Data;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.Map;

@Data
public class ErrorResponse {
    private int code;
    private String message;
    private Map<String, String> errorMap;
}
