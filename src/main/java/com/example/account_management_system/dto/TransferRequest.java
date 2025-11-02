package com.example.account_management_system.dto;

import lombok.Data;

@Data
public class TransferRequest {
    private Long from;
    private Long to;
    private double amount;
}
