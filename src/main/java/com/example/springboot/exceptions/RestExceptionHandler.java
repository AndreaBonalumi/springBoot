package com.example.springboot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse itemNotFoundException(ItemNotFoundException ex) {
        ErrorResponse error = new ErrorResponse();

        error.setCode(HttpStatus.NOT_FOUND.value());
        error.setMessage(ex.getMessage());

        return error;
    }
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse badRequestException(BadRequestException ex) {
        ErrorResponse error = new ErrorResponse();

        error.setCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage(ex.getMessage());

        return error;
    }
    @ExceptionHandler(NoAuthException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse noAuthException(NoAuthException ex) {
        ErrorResponse error = new ErrorResponse();

        error.setCode(HttpStatus.UNAUTHORIZED.value());
        error.setMessage(ex.getMessage());

        return error;
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse methodIllegalArgumentException(MethodArgumentNotValidException ex) {
        ErrorResponse error = new ErrorResponse();

        error.setCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage(ex.getMessage());
        Map<String, String> errorMap = new HashMap<>();

        for (ObjectError objectError : ex.getAllErrors()) {
            if (objectError instanceof FieldError fieldError) {
                String fieldName = fieldError.getField();
                String errorMessage = fieldError.getDefaultMessage();
                errorMap.put(fieldName, errorMessage);
            }
        }

        error.setErrorMap(errorMap);

        return error;
    }
}
