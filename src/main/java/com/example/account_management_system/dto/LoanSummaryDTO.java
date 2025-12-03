package com.example.account_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoanSummaryDTO {

    private long totalActiveLoans;
    private double totalLoanAmount;

}
