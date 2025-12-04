package com.example.account_management_system.model;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ATMCard {
    private String cardNumber;
    private String pin;
    private double balance;
    private int pinAttempts;
    private boolean blocked;
    private double dailyWithdrawn;

}
