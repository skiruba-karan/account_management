package com.example.account_management_system.controller;

import com.example.account_management_system.dto.StatementResponse;
import com.example.account_management_system.service.StatementService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/statement")
public class StatementController {

    private final StatementService statementService;

    public StatementController(StatementService statementService){
        this.statementService = statementService;
    }

    @GetMapping("/{accountId}")
    public StatementResponse getMonthlyStatement(
            @PathVariable Long accountId,
            @RequestParam int month,
            @RequestParam int year
    ){
        return statementService.generateMonthlyStatement(accountId, month, year);
    }

}
