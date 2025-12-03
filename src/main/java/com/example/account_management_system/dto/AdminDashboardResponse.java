package com.example.account_management_system.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AdminDashboardResponse {

    private long totalCustomers;
    private double totalDeposits;
    private List<TopAccountDTO> topAccounts;
    private LoanSummaryDTO loanSummary;

}
