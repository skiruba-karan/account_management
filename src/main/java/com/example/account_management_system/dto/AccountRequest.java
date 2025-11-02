package com.example.account_management_system.dto;

import lombok.Data;

@Data
public class AccountRequest {
    private String name;
    private double initialDeposit;
    private double amount;

}

