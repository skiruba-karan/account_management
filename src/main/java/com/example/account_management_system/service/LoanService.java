package com.example.account_management_system.service;

import com.example.account_management_system.dto.LoanRequest;
import com.example.account_management_system.dto.LoanResponse;
import com.example.account_management_system.exception.BankingException;
import com.example.account_management_system.model.Account;
import com.example.account_management_system.model.Loan;
import com.example.account_management_system.repository.AccountRepository;
import com.example.account_management_system.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final AccountRepository accountRepository;

    public LoanService(LoanRepository loanRepository, AccountRepository accountRepository){
        this.accountRepository = accountRepository;
        this.loanRepository = loanRepository;
    }

    public LoanResponse createLoan(LoanRequest request) {
        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> {
                    return new BankingException("Account not found for ID: " + request.getAccountId());
                });
        Loan loan = new Loan(account, request.getAmount(),request.getInterestRate(),request.getTenureMonths());
        loanRepository.save(loan);
        return LoanResponse.fromEntity(loan);
    }

    public LoanResponse getLoanById(Long loanId){
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(()->{return  new BankingException("Loan not Found for ID: "+loanId);});

        return LoanResponse.fromEntity(loan);
    }

    public List<LoanResponse> getAllLoans(Long accountId){
        List<Loan> loans = (accountId == null) ? loanRepository.findAll() :loanRepository.findByAccount_AccountId(accountId);
        return loans.stream()
                .map(LoanResponse::fromEntity)
                .collect(Collectors.toList());
    }


    public LoanResponse closeLoan(Long loanId){
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(()->{
                    return new BankingException("Loan not found for ID: "+loanId);
                    }
                );
                if(!loan.isActive()){
                        throw new BankingException("Loan already closed");
                }
                loan.setActive(false);
                loan.setClosedAt(LocalDateTime.now());
                loanRepository.save(loan);
                return LoanResponse.fromEntity(loan);

    }
}
