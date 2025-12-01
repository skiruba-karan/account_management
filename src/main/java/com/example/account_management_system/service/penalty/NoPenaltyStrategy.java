package com.example.account_management_system.service.penalty;

public class NoPenaltyStrategy implements PenaltyStrategy {
    @Override
    public double applyPenalty(double interestEarned){return 0;}
}
