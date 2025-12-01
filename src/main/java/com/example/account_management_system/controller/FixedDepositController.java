package com.example.account_management_system.controller;

import com.example.account_management_system.dto.FixedDepositRequest;
import com.example.account_management_system.dto.FixedDepositResponse;
import com.example.account_management_system.service.FixedDepositService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fixedDeposit")
public class FixedDepositController {
    private final FixedDepositService fixedDepositService;

    public FixedDepositController(FixedDepositService fixedDepositService) {
        this.fixedDepositService = fixedDepositService;
    }

    @PostMapping
    public FixedDepositResponse calculateFD(@Valid @RequestBody FixedDepositRequest request){
        return fixedDepositService.calculateFixedDepoosit(request);
    }
}
