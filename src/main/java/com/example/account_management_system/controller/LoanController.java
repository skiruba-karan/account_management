package com.example.account_management_system.controller;

import com.example.account_management_system.dto.LoanRequest;
import com.example.account_management_system.dto.LoanResponse;
import com.example.account_management_system.service.LoanService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {
    private final LoanService loanService;

    public LoanController(LoanService loanService){
        this.loanService = loanService;
    }

    @PostMapping
    public LoanResponse createLoan(@RequestBody LoanRequest request){
        return loanService.createLoan(request);
    }

    @GetMapping("/{loanId}")
    public LoanResponse getLoanById(@PathVariable Long loanId){
        return loanService.getLoanById(loanId);
    }
    @GetMapping
    public List<LoanResponse> getAllLoans(@RequestParam(required = false) Long accountId){
        return loanService.getAllLoans(accountId);
    }

    @PutMapping("/{loanId}/close")
    public LoanResponse closeLoan(@PathVariable Long loanId){
        return loanService.closeLoan(loanId);
    }

}
