package com.example.account_management_system.service;

import com.example.account_management_system.dto.LoanEligibilityRequest;
import com.example.account_management_system.dto.LoanEligibilityResponse;
import org.springframework.stereotype.Service;

@Service
public class LoanEligibilityService {

    private static final int MIN_AGE = 20;
    private static final double MIN_ANNUAL_INCOME = 300000;
    private static final int MIN_CREDIT_SCORE = 700;
    private static final double MAX_LOAN_TO_INCOME_RATIO = 0.4;

    public LoanEligibilityResponse checkEligibility(LoanEligibilityRequest request){
        if(request.getAge()<MIN_AGE){
            return new LoanEligibilityResponse("Not Eligible","Minimum age requirement not met",null);

        }
        if(request.getAnnualIncome()<=MIN_ANNUAL_INCOME){
            return new LoanEligibilityResponse("Not Eligible","Annual income below minimum requirement",null);
        }
        if(request.getCreditScore()<MIN_CREDIT_SCORE){
            return new LoanEligibilityResponse("Not Eligible","Credit score below minimum requirement",null);
        }

        double maxLoanAmount = request.getAnnualIncome() *1.2;
        return new LoanEligibilityResponse("Eligible",null,roundToTwoDecimal(maxLoanAmount));
    }

    private double roundToTwoDecimal(double value){
        return Math.round(value*100.0)/100.0;
    }
}

