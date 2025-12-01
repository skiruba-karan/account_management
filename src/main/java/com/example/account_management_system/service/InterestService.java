package com.example.account_management_system.service;

import com.example.account_management_system.dto.InterestRequest;
import com.example.account_management_system.dto.InterestResponse;
import org.springframework.stereotype.Service;

@Service
public class InterestService {
    public InterestResponse calculateInterest(InterestRequest request){
        double interest = (request.getPrincipal() * request.getRate() * request.getTime()) /100;
        double totalAmount = request.getPrincipal() + interest;
        return new InterestResponse(roundToDecimals(interest),roundToDecimals(totalAmount),"Calculation Completed");
    }

    private double roundToDecimals(double value){
        return Math.round(value*100)/100.0;
    }

}
