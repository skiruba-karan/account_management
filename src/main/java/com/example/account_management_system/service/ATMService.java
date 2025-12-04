package com.example.account_management_system.service;

import com.example.account_management_system.dto.ATMResponse;
import com.example.account_management_system.dto.CardValidationRequest;
import com.example.account_management_system.dto.PinVerificationRequest;
import com.example.account_management_system.dto.WithdrawRequest;
import com.example.account_management_system.model.ATMCard;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ATMService {

    private static final double DAILY_LIMIT = 10000;

    private final Map<String, ATMCard> cards = new HashMap<>();

    public ATMService(){
        cards.put("1234567890",ATMCard.builder()
                        .cardNumber("1234567890")
                        .pin("1234")
                        .balance(20000)
                        .pinAttempts(0)
                        .blocked(false)
                        .dailyWithdrawn(0)
                        .build());
    }

    public ATMResponse validateCard(CardValidationRequest request){
        ATMCard card = cards.get(request.getCardNumber());
        if(card == null){
            return ATMResponse.builder()
                    .message("Card is blocked due to multiple PIN failures")
                    .build();
        }
        return ATMResponse.builder()
                .message("Card validated successfully")
                .build();
    }

    public ATMResponse verifyPin(PinVerificationRequest request){
        ATMCard card = cards.get(request.getCardNumber());
        if(card == null){
            return ATMResponse.builder()
                    .message("Invalid card number")
                    .build();
        }
        if(card.isBlocked()){
            return ATMResponse.builder()
                    .message("Card is blocked due to incorrect PIN attempts")
                    .build();
        }

        if(!card.getPin().equals(request.getPin())){
            card.setPinAttempts(card.getPinAttempts()+1);
            if(card.getPinAttempts() >= 3){
                card.setBlocked(true);
                return ATMResponse.builder()
                        .message("Incorrect PIN. Card Blocked.")
                        .build();
            }
            return ATMResponse.builder()
                    .message("Incorrect PIN. Attempts left: "+(3 - card.getPinAttempts()))
                    .build();
        }
        card.setPinAttempts(0);
        return ATMResponse.builder()
                .message("PIN Verified. Authenticated")
                .build();
    }

    public ATMResponse withdraw(WithdrawRequest request){
        ATMCard card = cards.get((request.getCardNumber()));

        if(card == null){
            return ATMResponse.builder()
                    .message("Invalid card number")
                    .build();
        }

        if(card.isBlocked()){
            return ATMResponse.builder()
                    .message("Card is blocked")
                    .build();
        }

        if(card.getDailyWithdrawn()+request.getAmount()>DAILY_LIMIT){
            return ATMResponse.builder()
                    .message("Daily withdrawal limit exceeded")
                    .balance(card.getBalance())
                    .build();
        }
        if(request.getAmount() > card.getBalance()){
            return ATMResponse.builder()
                    .message("Insufficient balance")
                    .balance(card.getBalance())
                    .build();
        }
        card.setBalance(card.getBalance() - request.getAmount());
        card.setDailyWithdrawn(card.getDailyWithdrawn()+request.getAmount());

        return ATMResponse.builder()
                .message("Withdrawal successful. Remaining balance: "+card.getBalance())
                .balance(card.getBalance())
                .build();
    }

    public ATMResponse checkBalance(String cardNumber) {
        ATMCard card = cards.get(cardNumber);
        if(card == null){
            return ATMResponse.builder()
                    .message("Invalid card number")
                    .build();
        }

        return ATMResponse.builder()
                .message("Balance fetched successfully")
                .balance(card.getBalance())
                .build();
    }

}
