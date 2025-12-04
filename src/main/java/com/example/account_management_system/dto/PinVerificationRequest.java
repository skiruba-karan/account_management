package com.example.account_management_system.dto;

import lombok.Data;

@Data
public class PinVerificationRequest {

    private String cardNumber;
    private String pin;

}
