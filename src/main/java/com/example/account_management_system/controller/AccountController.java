package com.example.account_management_system.controller;

import com.example.account_management_system.dto.AccountRequest;
import com.example.account_management_system.dto.TransferRequest;
import com.example.account_management_system.model.Account;
import com.example.account_management_system.model.Transaction;
import com.example.account_management_system.repository.TransactionRepository;
import com.example.account_management_system.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;
    private final TransactionRepository transactionRepository;

    public AccountController(AccountService accountService, TransactionRepository transactionRepository){
        this.accountService = accountService;
        this.transactionRepository = transactionRepository;
    }

    @PostMapping
    public Map<String,Object> createAccount(@RequestBody AccountRequest request){
        Account account = accountService.createAccount(request.getName(),request.getInitialDeposit());
        return Map.of("accountID",account.getAccountId(),"balance",account.getBalance());
    }

    @GetMapping
    public List<Account> getAllAccount(){
        return accountService.getAllAccounts();
    }

    @GetMapping("/{id}")
    public Account getAccount(@PathVariable Long id) {
        return accountService.getAccount(id);
    }

    @PostMapping("/{id}/deposit")
    public Map<String,Object> deposit(@PathVariable Long id,@RequestBody AccountRequest request){
        return accountService.deposit(id,request.getAmount());
    }

    @PostMapping("/{id}/withdraw")
    public Map<String,Object> withdraw(@PathVariable Long id,@RequestBody AccountRequest request){
        return accountService.withdraw(id,request.getAmount());
    }

    @PostMapping("/transfer")
    public Map<String,Object> transfer(@RequestBody TransferRequest request){
        return accountService.transferFunds(
                request.getFrom(),
                request.getTo(),
                request.getAmount()
        );
    }

    @GetMapping("/{accountId}/transactions")
    public List<Transaction> getAllTransactionHistory(@PathVariable Long accountId,
                                                      @RequestParam(required = false,defaultValue = "10") int limit,
                                                      @RequestParam(required = false) String type){
        List<Transaction> records;
        if(type!=null && !type.isEmpty()){
            records = transactionRepository.findByAccountIdAndType(accountId,type);
        }else{
            records = transactionRepository.findRecentTransactions(accountId);
        }
        return records.stream().limit(limit).toList();
    }

}
