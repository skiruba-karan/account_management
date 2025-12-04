package com.example.account_management_system.dto;

import lombok.Data;

@Data
public class WithdrawRequest {

    private String cardNumber;
    private double amount;

}
