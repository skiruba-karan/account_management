package com.example.account_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ATMCardResponse {

    private Long accountId;
    private String cardNumber;
    private boolean blocked;
    private int pinAttempts;
    private double dailyWithdrawn;

}
