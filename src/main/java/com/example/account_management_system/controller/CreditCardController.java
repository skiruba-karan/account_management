package com.example.account_management_system.controller;

import com.example.account_management_system.dto.CreditCardBillRequest;
import com.example.account_management_system.dto.CreditCardBillResponse;
import com.example.account_management_system.service.CreditCardBillService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/creditCard")
public class CreditCardController {

    private final CreditCardBillService creditCardBillService;

    public CreditCardController(CreditCardBillService creditCardBillService){
        this.creditCardBillService  = creditCardBillService;
    }

    @PostMapping("/bill")
    public CreditCardBillResponse calculateBill(@RequestBody CreditCardBillRequest request){
        return creditCardBillService.calculateBill(request);
    }

}
