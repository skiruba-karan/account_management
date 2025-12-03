package com.example.account_management_system.service;

import com.example.account_management_system.dto.CreditCardBillRequest;
import com.example.account_management_system.dto.CreditCardBillResponse;
import com.example.account_management_system.service.strategy.InterestStrategy;
import com.example.account_management_system.service.strategy.LatePaymentStrategy;
import com.example.account_management_system.service.strategy.NoInterestStrategy;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class CreditCardBillService {

    public CreditCardBillResponse calculateBill(CreditCardBillRequest request){
        double pendingAmount = request.getTotalSpending() - request.getPaymentMade();
        pendingAmount = Math.max(pendingAmount,0);
        LocalDate due = LocalDate.parse(request.getDueDate());
        LocalDate current = LocalDate.parse(request.getCurrentDate());

        long daysLate = ChronoUnit.DAYS.between(due,current);

        boolean isLate = daysLate > 0;

        InterestStrategy strategy = isLate ?
                new LatePaymentStrategy() :
                new NoInterestStrategy();

        double interest = strategy.calculateInterest(pendingAmount,Math.max(daysLate,0));
        String status = isLate ? "Overdue" : "On Time";

        return CreditCardBillResponse.builder()
                .pendingAmount(pendingAmount)
                .interest(interest)
                .status(status)
                .daysLate(Math.max(daysLate,0))
                .build();
    }
}
