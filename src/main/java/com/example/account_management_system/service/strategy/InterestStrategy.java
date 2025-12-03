package com.example.account_management_system.service.strategy;

public interface InterestStrategy {
    double calculateInterest(double pendingAmount, long daysLate);
}
