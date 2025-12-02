package com.example.account_management_system.service;

import com.example.account_management_system.dto.StatementResponse;
import com.example.account_management_system.exception.BankingException;
import com.example.account_management_system.model.Account;
import com.example.account_management_system.model.Transaction;
import com.example.account_management_system.repository.AccountRepository;
import com.example.account_management_system.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

@Service
public class StatementService
{

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public StatementService(TransactionRepository transactionRepository, AccountRepository accountRepository)
    {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public StatementResponse generateMonthlyStatement(Long accountId, int month, int year){
        Account account = accountRepository.findById(accountId)
                .orElseThrow(()->{
                    return new BankingException("Account not found for ID: "+accountId);
                });
        LocalDateTime startDate = LocalDateTime.of(year,month,1,0,0);
        LocalDateTime endDate = startDate.plusMonths(1);

        List<Transaction> transactions = transactionRepository.findByAccountIdAndTimestamp(accountId,startDate,endDate);

        double totalDeposits = transactions.stream()
                .filter(tx -> tx.getType().equalsIgnoreCase("WITHDRAW") ||
                        tx.getType().equalsIgnoreCase("TRANSFER_OUT"))
                .mapToDouble(Transaction::getAmount)
                .sum();

        double totalWithdrawals = transactions.stream()
                .filter(tx->tx.getType().equalsIgnoreCase("WITHDRAW")||
                        tx.getType().equalsIgnoreCase("TRANSFER_OUT"))
                .mapToDouble(Transaction::getAmount)
                .sum();

        double closingBalance = account.getBalance();
        double netChange = totalDeposits - totalWithdrawals;
        double openingBalance = closingBalance - netChange;

        return StatementResponse.builder()
                .accountId(account.getAccountId())
                .name(account.getName())
                .month(Month.of(month).name())
                .openingBalance(round(openingBalance))
                .totalDeposits(round(totalDeposits))
                .totalWithdrawals(round(totalWithdrawals))
                .closingBalance(round(closingBalance))
                .build();

    }

    private double round(double value){
        return Math.round(value*100.0)/100.0;
    }

}
