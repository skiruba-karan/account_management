package com.example.account_management_system.dto;

import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FixedDepositRequest {
    @Min(value=1,message = "Deposit amount must be greater than zero")
    private final double amount;

    @Min(value = 1, message = "Interest Rate must be greater than zero")
    private final double interestRate;

    @Min(value=1, message = "Tenure must be greater than zero")
    private final int tenure;

    private final boolean premature;
}
