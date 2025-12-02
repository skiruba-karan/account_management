package com.example.account_management_system.controller;

import com.example.account_management_system.dto.LoanEligibilityRequest;
import com.example.account_management_system.dto.LoanEligibilityResponse;
import com.example.account_management_system.service.LoanEligibilityService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loanEligibility")
public class LoanEligibilityController {

    private final LoanEligibilityService loanEligibilityService;

    public LoanEligibilityController(LoanEligibilityService loanEligibilityService){
        this.loanEligibilityService = loanEligibilityService;
    }

    @PostMapping
    public ResponseEntity<LoanEligibilityResponse> checkEligibility(@Valid @RequestBody LoanEligibilityRequest request){
        LoanEligibilityResponse response = loanEligibilityService.checkEligibility(request);
        return ResponseEntity.ok(response);
    }

}
