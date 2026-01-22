package com.example.account_management_system.dto;

import lombok.Data;

@Data
public class CreateCardRequest {
    private Long accountId;
    private String cardNumber;
    private String pin;
}
