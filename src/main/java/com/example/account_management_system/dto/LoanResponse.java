package com.example.account_management_system.dto;

import com.example.account_management_system.model.Loan;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class LoanResponse {
    private Long loanId;
    private Long accountId;
    private Double amount;
    private Double interestRate;
    private Integer tenureMonths;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime closedAt;

    public static LoanResponse fromEntity(Loan loan){
        return LoanResponse.builder()
                .loanId(loan.getId())
                .accountId(loan.getAccount().getAccountId())
                .amount(loan.getAmount())
                .interestRate(loan.getInterestRate())
                .tenureMonths(loan.getTenureMonths())
                .active(loan.isActive())
                .createdAt(loan.getCreatedAt())
                .closedAt(loan.getClosedAt())
                .build();
    }
}
