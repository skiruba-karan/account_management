package com.example.account_management_system.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InterestRequest {
    @NotNull(message = "Principal is required")
    @DecimalMin(value="0.0",inclusive = false,message = "Principal must be a positive number")
    private Double principal;

    @NotNull(message = "Rate is required")
    @DecimalMin(value="0.0",inclusive = false,message = "Rate must be a positive number")
    private Double rate;

    @NotNull(message = "Time is required")
    @DecimalMin(value="0.0",inclusive = false,message = "Time must be a positive number")
    private Double time;
}
