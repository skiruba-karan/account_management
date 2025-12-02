package com.example.account_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoanEligibilityResponse {
    private String status;
    private String reason;
    private Double maxLoanAmount;
}
