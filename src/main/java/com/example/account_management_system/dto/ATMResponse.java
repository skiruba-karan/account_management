package com.example.account_management_system.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ATMResponse {

    private String message;
    private double balance;

}
