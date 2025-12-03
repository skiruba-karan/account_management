package com.example.account_management_system.service.strategy;

public class LatePaymentStrategy implements InterestStrategy{
    private static final double MONTHLY_INTEREST_RATE = 0.05;

    @Override
    public double calculateInterest(double pendingAmount, long daysLate){
        if(pendingAmount <= 0){
            return 0;
        }
        return pendingAmount * MONTHLY_INTEREST_RATE;
    }
}
