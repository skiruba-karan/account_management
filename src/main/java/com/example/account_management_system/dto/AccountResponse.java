package com.example.account_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountResponse {
    private Long accountId;
    private Long userId;
    private String name;
    private double balance;
}
