package com.example.account_management_system.service;

import com.example.account_management_system.exception.BankingException;
import com.example.account_management_system.model.Account;
import com.example.account_management_system.repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AccountService {

    private final AccountRepository accountRepository;


    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    public Account createAccount(String name,double initalDeposit){
        Account account = new Account(name,initalDeposit);
        return accountRepository.save(account);

    }

    public Account getAccount(Long id){
        return accountRepository.findById(id).orElseThrow(()->new RuntimeException("Account not found"));
    }

    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

    @Transactional
    public Map<String,Object> deposit(Long id, double amount){
        Account account = accountRepository.findById(id)
                .orElseThrow(()->new BankingException("Account not found"));
        account.setBalance(account.getBalance()+amount);
        accountRepository.save(account);
        return Map.of("accountID",id,"balance",account.getBalance());
    }

    @Transactional
    public Map<String,Object> withdraw(Long id, double amount){
        Account account = accountRepository.findById(id)
                .orElseThrow(()->new BankingException("Account not found"));

        if(account.getBalance()<amount){
            throw new BankingException("Insufficient Balance");
        }
        account.setBalance(account.getBalance()-amount);
        accountRepository.save(account);
        return Map.of("accountID",id,"balance",account.getBalance());

    }


    @Transactional
    public Map<String,Object> transferFunds(Long fromAccountId, Long toAccountId, double amount){
        if(fromAccountId == null){
            throw new BankingException("From Account ID must not be null");
        }
        if(toAccountId == null){
            throw new BankingException("To Account ID must not be null");
        }
        if(fromAccountId.equals(toAccountId)){
            throw new BankingException("Sender and Receiver accounts cannot be the same");
        }
        Account fromAccount = accountRepository.findById(fromAccountId)
                .orElseThrow(()->new BankingException("Sender account not found"));
        Account toAccount = accountRepository.findById(toAccountId)
                .orElseThrow(()->new BankingException("Receiver account not found"));

        if(fromAccount.getBalance()<amount){
            throw new BankingException("Insufficient Balance");
        }
        fromAccount.setBalance(fromAccount.getBalance()-amount);
        toAccount.setBalance(toAccount.getBalance()+amount);

        return Map.of(
           "statusMessage","Transaction Completed",
           "fromAccount",fromAccountId,
           "toAccount",toAccountId,
           "amountTransferred",amount,
           "remainingBalance",fromAccount.getBalance()
        );

    }


}
