package com.example.account_management_system.dto;

import lombok.Data;

@Data
public class CreditCardBillRequest {

    private double totalSpending;
    private double paymentMade;
    private String dueDate;
    private String currentDate;

}
