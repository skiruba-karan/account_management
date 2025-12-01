package com.example.account_management_system.service;

import com.example.account_management_system.dto.FixedDepositRequest;
import com.example.account_management_system.dto.FixedDepositResponse;
import com.example.account_management_system.exception.InvalidInputException;
import com.example.account_management_system.service.penalty.NoPenaltyStrategy;
import com.example.account_management_system.service.penalty.PenaltyStrategy;
import com.example.account_management_system.service.penalty.PrematurePenaltyStrategy;
import org.springframework.stereotype.Service;

@Service
public class FixedDepositService {
    public FixedDepositResponse calculateFixedDepoosit(FixedDepositRequest request){
        if(request.getAmount()<0){
            throw new InvalidInputException("Amount cannot be negative");
        }
        if(request.getTenure()<=0){
            throw new InvalidInputException("Tenure must be greater than zero");
        }
        if(request.getInterestRate()<0){
            throw new InvalidInputException("Interest rate cannot be negative");
        }
        if(request.getAmount()==0){
            return new FixedDepositResponse(0.0,0.0,request.isPremature(),0.0,"Calculation Successful");
        }

        double maturityAmount = calculateCompoundInterest(
                request.getAmount(),
                request.getInterestRate(),
                request.getTenure()
        );

        double interestEarned = maturityAmount - request.getAmount();
        PenaltyStrategy penaltyStrategy = request.isPremature()
                ? new PrematurePenaltyStrategy()
                : new NoPenaltyStrategy();
        double penaltyAmount = penaltyStrategy.applyPenalty(interestEarned);

        double finalInterest = interestEarned - penaltyAmount;
        double finalMaturityAmount = request.getAmount() + finalInterest;

        return new FixedDepositResponse(
                roundOff(finalMaturityAmount),
                roundOff(finalInterest),
                request.isPremature(),
                roundOff(penaltyAmount),
                "Calculation Successful"
        );
    }
    private double calculateCompoundInterest(double principal, double rate, int time){
        return principal * Math.pow(1+(rate/100),time);
    }

    private double roundOff(double value){
        return Math.round(value * 100.0)/100.0;
    }
}
