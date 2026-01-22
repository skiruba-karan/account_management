package com.example.account_management_system.service;

import com.example.account_management_system.dto.*;
import com.example.account_management_system.model.ATMCard;
import com.example.account_management_system.model.Account;
import com.example.account_management_system.repository.ATMCardRepository;
import com.example.account_management_system.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ATMService {

    private static final double DAILY_LIMIT = 10_000.0;

    private final ATMCardRepository atmCardRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public ATMCardResponse createCard(CreateCardRequest request){
        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(()->new IllegalArgumentException("Account not found: "+request.getAccountId()));

        if(atmCardRepository.existsByAccountAccountId(account.getAccountId())){
            throw new IllegalStateException("ATM card already exists for account: "+account.getAccountId());
        }

        if(atmCardRepository.existsByCardNumber(request.getCardNumber())){
            throw new IllegalStateException("Card number already in use: "+request.getCardNumber());
        }

        String pinToStore = request.getPin();

        ATMCard card = new ATMCard(account,request.getCardNumber(),pinToStore);
        card.setBlocked(false);
        card.setPinAttempts(0);
        card.setDailyWithdrawn(0.0);

        ATMCard saved = atmCardRepository.save(card);

        return new ATMCardResponse(
                saved.getAccountId(),
                saved.getCardNumber(),
                saved.isBlocked(),
                saved.getPinAttempts(),
                saved.getDailyWithdrawn()
        );
    }

    public ATMResponse validateCard(CardValidationRequest request){
        var cardOpt = atmCardRepository.findByCardNumber(request.getCardNumber());
        if(cardOpt.isEmpty()){
            return ATMResponse.builder().message("Invalid card number").build();
        }
        var card = cardOpt.get();
        if(card.isBlocked()){
            return ATMResponse.builder().message("Card is blocked due to multiple PIN failures").build();

        }
        return ATMResponse.builder().message("Card validated successfully").build();
    }

    public ATMResponse verifyPin(PinVerificationRequest request){
        var cardOpt = atmCardRepository.findByCardNumber(request.getCardNumber());
        if(cardOpt.isEmpty()){
            return ATMResponse.builder().message("Invalid card number").build();
        }
        var card = cardOpt.get();

        if(card.isBlocked()){
            return ATMResponse.builder().message("Card is blicked due to incorrect PIN attempts").build();
        }
        if(!card.getPin().equals(request.getPin())){
            card.setPinAttempts(card.getPinAttempts() + 1);
            if(card.getPinAttempts() >= 3){
                card.setBlocked(true);
                atmCardRepository.save(card);
                return ATMResponse.builder().message("Incorrect PIN. Card Blocked").build();
            }
            atmCardRepository.save(card);
            return ATMResponse.builder()
                    .message("Incorrect PIN. Attempts left: "+(3-card.getPinAttempts()))
                    .build();
        }

        card.setPinAttempts(0);
        atmCardRepository.save(card);
        return ATMResponse.builder().message("PIN Verified. Authenticated.").build();
    }



}
