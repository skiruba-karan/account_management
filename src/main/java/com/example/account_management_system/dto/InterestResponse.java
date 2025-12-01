package com.example.account_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InterestResponse {
    private final double interest;
    private final double totalAmount;
    private String message;
}

