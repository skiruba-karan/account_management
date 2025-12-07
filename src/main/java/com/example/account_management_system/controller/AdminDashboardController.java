package com.example.account_management_system.controller;

import com.example.account_management_system.dto.AdminDashboardResponse;
import com.example.account_management_system.dto.LoanSummaryDTO;
import com.example.account_management_system.service.AdminDashboardService;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/totalCustomers")
    public long getTotalCustomers(){
        return adminDashboardService.getTotalCustomers();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/totalDeposits")
    public double getTotalDeposits() {
        return adminDashboardService.getTotalDeposits();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/topAccounts")
    public AdminDashboardResponse getTopAccounts(){
        return adminDashboardService.getTopAccounts();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/loanSummary")
    public LoanSummaryDTO getLoanSummary(){
        return adminDashboardService.getLoanSummaryOnly();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/fullReport")
    public AdminDashboardResponse getFullReport(){
        return adminDashboardService.getAdminDashboardReport();
    }

}
