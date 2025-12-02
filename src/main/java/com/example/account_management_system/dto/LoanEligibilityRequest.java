package com.example.account_management_system.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanEligibilityRequest {
    @NotNull(message = "Age is required")
    @Min(value = 20, message = "Age must be atleast 20")
    @Max(value = 60, message="Age must be less than 60")
    private int age;

    @NotNull(message = "Annual Income is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Annual income must be positive")
    private double annualIncome;

    @NotNull(message = "Credit score is required")
    @Min(value = 20, message = "Credit score must be atleast 300")
    @Max(value = 60, message="Credit score must not exceed 900")
    private int creditScore;

    @NotNull(message = "Existing loan amount is required")
    @DecimalMin(value="0.0", inclusive = true,message = "Existing loan amount must be positive")
    private double existingLoanAmount;




}
