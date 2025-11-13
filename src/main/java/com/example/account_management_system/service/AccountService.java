package com.example.account_management_system.service;

import com.example.account_management_system.exception.BankingException;
import com.example.account_management_system.model.Account;
import com.example.account_management_system.model.Transaction;
import com.example.account_management_system.repository.AccountRepository;
import com.example.account_management_system.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;


    public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    private void logTransaction(Long accountId, String type, double amount){
        Transaction tr = new Transaction();
        tr.setAccountId(accountId);
        tr.setType(type);
        tr.setAmount(amount);
        tr.setTimestamp(LocalDateTime.now());
        transactionRepository.save(tr);
    }

    public Account createAccount(String name,double initalDeposit){
        Account account = new Account(name,initalDeposit);
        Account savedAccount = accountRepository.save(account);
        logTransaction(savedAccount.getAccountId(),"INITIAL_DEPOSIT",initalDeposit);
        return savedAccount;


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
        logTransaction(id,"DEPOSIT",amount);
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
        logTransaction(id,"WITHDRAW",amount);
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

        logTransaction(fromAccountId,"TRANSFER_OUT",amount);
        logTransaction(toAccountId,"TRANSFER_IN",amount);

        return Map.of(
           "statusMessage","Transaction Completed",
           "fromAccount",fromAccountId,
           "toAccount",toAccountId,
           "amountTransferred",amount,
           "remainingBalance",fromAccount.getBalance()
        );

    }


}
