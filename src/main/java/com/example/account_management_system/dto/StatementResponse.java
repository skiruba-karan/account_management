package com.example.account_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatementResponse {
    private Long accountId;
    private String name;
    private String month;
    private double openingBalance;
    private double totalDeposits;
    private double totalWithdrawals;
    private double closingBalance;
}
