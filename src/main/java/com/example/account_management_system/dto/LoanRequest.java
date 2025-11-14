package com.example.account_management_system.dto;

import lombok.Data;

@Data
public class LoanRequest {
    private Long accountId;
    private Double amount;
    private Double interestRate;
    private Integer tenureMonths;
}
