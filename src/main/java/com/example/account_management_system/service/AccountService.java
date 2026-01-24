package com.example.account_management_system.service;

import com.example.account_management_system.exception.BankingException;
import com.example.account_management_system.model.Account;
import com.example.account_management_system.model.Transaction;
import com.example.account_management_system.model.User;
import com.example.account_management_system.repository.AccountRepository;
import com.example.account_management_system.repository.TransactionRepository;
import com.example.account_management_system.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;


    public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    private void logTransaction(Account account, String type, double amount){
        Transaction tr = new Transaction();
        tr.setAccount(account);
        tr.setType(type);
        tr.setAmount(amount);
        tr.setTimestamp(LocalDateTime.now());
        transactionRepository.save(tr);
    }

    @Transactional
    public Account createAccountForUser(Long userId, String name, double initialDeposit){
        if(userId == null){
            throw new BankingException("User id must not be null");
        }
        if(name == null || name.isBlank()){
            throw new BankingException("Account name must not be blank");
        }
        if(initialDeposit < 0){
            throw new BankingException("Initial deposit must be non-negative");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new BankingException("User not found"));

        Account account = new Account(user, name, initialDeposit);
        Account savedAccount = accountRepository.save(account);
        if(initialDeposit > 0){
            logTransaction(savedAccount, "INITIAL_DEPOSIT",initialDeposit);
        }

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
        if(amount <=0){
            throw new BankingException("Deposit amount must be greated than 0");
        }
        Account account = accountRepository.findById(id)
                .orElseThrow(()->new BankingException("Account not found"));
        account.setBalance(account.getBalance()+amount);
        accountRepository.save(account);
        logTransaction(account,"DEPOSIT",amount);
        return Map.of("accountID",id,"balance",account.getBalance());
    }

    @Transactional
    public Map<String,Object> withdraw(Long id, double amount){
        if(amount <=0){
            throw new BankingException("Withdrawal amount must be greater than 0");
        }
        Account account = accountRepository.findById(id)
                .orElseThrow(()->new BankingException("Account not found"));

        if(account.getBalance()<amount){
            throw new BankingException("Insufficient Balance");
        }
        account.setBalance(account.getBalance()-amount);
        accountRepository.save(account);
        logTransaction(account,"WITHDRAW",amount);
        return Map.of("accountID",id,"balance",account.getBalance());

    }
    public List<Account> getAllAccount(){
        return accountRepository.findAll();
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

        logTransaction(fromAccount,"TRANSFER_OUT",amount);
        logTransaction(toAccount,"TRANSFER_IN",amount);

        return Map.of(
           "statusMessage","Transaction Completed",
           "fromAccount",fromAccountId,
           "toAccount",toAccountId,
           "amountTransferred",amount,
           "remainingBalance",fromAccount.getBalance()
        );

    }


}
