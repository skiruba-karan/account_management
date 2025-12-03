package com.example.account_management_system.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreditCardBillResponse {

    private double pendingAmount;
    private double interest;
    private String status;
    private long daysLate;

}
