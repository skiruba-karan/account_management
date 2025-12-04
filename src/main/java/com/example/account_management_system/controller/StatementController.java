package com.example.account_management_system.controller;

import com.example.account_management_system.dto.StatementResponse;
import com.example.account_management_system.service.StatementService;
import com.example.account_management_system.utils.StatementExportUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
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

    @GetMapping("/{accountId}/export")
    public ResponseEntity<?> exportStatement(
            @PathVariable Long accountId,
            @RequestParam int month,
            @RequestParam int year,
            @RequestParam String type
    ){
        StatementResponse response = statementService.generateMonthlyStatement(accountId,month,year);
        if("csv".equalsIgnoreCase(type)){
            byte[] data = StatementExportUtil.exportToCSV(response);
            return ResponseEntity.ok()
                    .header("Content-Disposition","attachment; filename = statement.csv")
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(data);
        }else if("pdf".equalsIgnoreCase(type)){
            byte[] data = StatementExportUtil.exportToPDF(response);
            return ResponseEntity.ok()
                    .header("Content-Disposition","attachment; filename = statement.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(data);
            
        }else {
            throw new IllegalArgumentException("Unsupported export type: "+type);
        }
    }

}
