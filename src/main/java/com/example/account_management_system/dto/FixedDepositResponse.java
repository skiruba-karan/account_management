package com.example.account_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FixedDepositResponse {
    private final double maturityAmount;
    private final double interestEarned;
    private final boolean prematureWithdrawal;
    private final double penaltyDeducted;
    private final String message;
}
