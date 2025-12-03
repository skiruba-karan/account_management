package com.example.account_management_system.service.strategy;

public class NoInterestStrategy implements InterestStrategy{
    @Override
    public double calculateInterest(double pendingAmount, long daysLate){
        return 0;
    }
}
