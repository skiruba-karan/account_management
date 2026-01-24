package com.example.account_management_system.controller;

import com.example.account_management_system.dto.*;
import com.example.account_management_system.service.ATMService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/atm")
public class ATMController {

    private final ATMService atmService;

    public ATMController(ATMService atmService){
        this.atmService = atmService;
    }

    @PostMapping("/createCard")
    public ATMCardResponse createCard(@RequestBody CreateCardRequest request){
        return atmService.createCard(request);
    }

    @PostMapping("/validateCard")
    public ATMResponse validateCard(@RequestBody CardValidationRequest request){
        return atmService.validateCard(request);
    }

    @PostMapping("/verifyPin")
    public ATMResponse verifyPin(@RequestBody PinVerificationRequest request){
        return atmService.verifyPin(request);
    }

    @PostMapping("/withdraw")
    public ATMResponse withdraw(@RequestBody WithdrawRequest request){
        return atmService.withdraw(request);
    }

    @GetMapping("/balance/{cardNumber}")
    public ATMResponse getBalance(@PathVariable String cardNumber){
        return atmService.checkBalance(cardNumber);
    }

}
