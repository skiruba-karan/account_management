package com.example.account_management_system.controller;

import com.example.account_management_system.dto.AdminDashboardResponse;
import com.example.account_management_system.dto.LoanSummaryDTO;
import com.example.account_management_system.service.AdminDashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminDashboardController {

    private final AdminDashboardService adminDashboardService;

    public AdminDashboardController(AdminDashboardService adminDashboardService){
        this.adminDashboardService = adminDashboardService;
    }
    @GetMapping("/totalCustomers")
    public long getTotalCustomers(){
        return adminDashboardService.getTotalCustomers();
    }

    @GetMapping("/totalDeposits")
    public double getTotalDeposits() {
        return adminDashboardService.getTotalDeposits();
    }

    @GetMapping("/topAccounts")
    public AdminDashboardResponse getTopAccounts(){
        return adminDashboardService.getTopAccounts();
    }

    @GetMapping("/loanSummary")
    public LoanSummaryDTO getLoanSummary(){
        return adminDashboardService.getLoanSummaryOnly();
    }

    @GetMapping("/fullReport")
    public AdminDashboardResponse getFullReport(){
        return adminDashboardService.getAdminDashboardReport();
    }

}
