package com.example.account_management_system.controller;

import com.example.account_management_system.dto.InterestRequest;
import com.example.account_management_system.dto.InterestResponse;
import com.example.account_management_system.service.InterestService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calculateInterest")
public class InterestController {
    private final InterestService interestService;

    public InterestController(InterestService interestService){
        this.interestService = interestService;

    }

    @PostMapping
    public ResponseEntity<InterestResponse> calculate(@Valid @RequestBody InterestRequest request){
        InterestResponse response = interestService.calculateInterest(request);
        return ResponseEntity.ok(response);
    }
}
