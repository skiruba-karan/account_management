package com.example.account_management_system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BankingException.class)
    public ResponseEntity<Map<String,Object>> handleBankingException(BankingException ex){
        Map<String,Object> response = Map.of(
                "timestamp", LocalDateTime.now(),
                "error",ex.getMessage(),
                "status",HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>> handleGeneralException(Exception ex){
        Map<String,Object> response = Map.of(
                "timestamp", LocalDateTime.now(),
                "error",ex.getMessage(),
                "status",HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
