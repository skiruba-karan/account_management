package com.example.account_management_system.service.penalty;

public class PrematurePenaltyStrategy implements PenaltyStrategy{
    private static final double PENALTY_RATE=0.01;
    @Override
    public double applyPenalty(double interestEarned) {return interestEarned * PENALTY_RATE;}
}
