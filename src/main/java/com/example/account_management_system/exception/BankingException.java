package com.example.account_management_system.exception;

public class BankingException extends RuntimeException{
    public BankingException(String message){
        super(message);
    }
}
