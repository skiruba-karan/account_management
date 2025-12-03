package com.example.account_management_system.service;

import com.example.account_management_system.dto.AdminDashboardResponse;
import com.example.account_management_system.dto.LoanSummaryDTO;
import com.example.account_management_system.dto.TopAccountDTO;
import com.example.account_management_system.repository.AccountRepository;
import com.example.account_management_system.repository.LoanRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminDashboardService {

    private final AccountRepository accountRepository;
    private final LoanRepository loanRepository;

    public AdminDashboardService(AccountRepository accountRepository, LoanRepository loanRepository){
        this.accountRepository = accountRepository;
        this.loanRepository = loanRepository;
    }

   @Cacheable("totalCustomers")
    public long getTotalCustomers(){
        long count = accountRepository.countDistinctCustomers();
        return accountRepository.countDistinctCustomers();
    }

    @Cacheable("totalDeposits")
    public double getTotalDeposits(){
        double total = accountRepository.sumAllBalances();
        return accountRepository.sumAllBalances();
    }

    @Cacheable("topAccounts")
    public AdminDashboardResponse getTopAccounts(){
        List<TopAccountDTO> topAccounts = accountRepository.findTopBalanceAccounts(50000.0);
        return AdminDashboardResponse.builder()
                .totalCustomers(getTotalCustomers())
                .totalDeposits(getTotalDeposits())
                .topAccounts(topAccounts)
                .build();
    }

    public LoanSummaryDTO getLoanSummaryOnly(){
        return loanRepository.getLoanSummary();
    }

    @Cacheable("loanSummary")
    public AdminDashboardResponse getLoanSummary(){
        LoanSummaryDTO loanSummary = loanRepository.getLoanSummary();
        return AdminDashboardResponse.builder()
                .loanSummary(loanSummary)
                .build();
    }

    @Cacheable("adminReport")
    public AdminDashboardResponse getAdminDashboardReport(){
        return AdminDashboardResponse.builder()
                .totalCustomers(getTotalCustomers())
                .topAccounts(accountRepository.findTopBalanceAccounts(50000.0))
                .loanSummary(loanRepository.getLoanSummary())
                .build();
    }

}
