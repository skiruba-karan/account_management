package com.example.account_management_system.controller;

import com.example.account_management_system.dto.AccountRequest;
import com.example.account_management_system.dto.AccountResponse;
import com.example.account_management_system.dto.TransferRequest;
import com.example.account_management_system.model.Account;
import com.example.account_management_system.model.Transaction;
import com.example.account_management_system.repository.TransactionRepository;
import com.example.account_management_system.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/accounts")
@Tag(
        name = "Account Management",
        description = "APIs for creating accounts, deposits, withdrawals, and transfers"
)
@SecurityRequirement(name = "JWT")
public class AccountController {
    private final AccountService accountService;
    private final TransactionRepository transactionRepository;

    public AccountController(AccountService accountService, TransactionRepository transactionRepository){
        this.accountService = accountService;
        this.transactionRepository = transactionRepository;
    }
    @Operation(
            summary = "Create a new bank account",
            description = "Creates a new account with an initial deposit"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Account created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping
    public Map<String,Object> createAccount(@RequestBody AccountRequest request){
        Account account = accountService.createAccountForUser(
                request.getUserId(),
                request.getName(),
                request.getInitialDeposit());
        return Map.of("accountID",account.getAccountId(),"balance",account.getBalance());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Account> getAllAccount(){
        return accountService.getAllAccounts();
    }

    @GetMapping("/{id}")
    public AccountResponse getAccount(@PathVariable Long id) {
        Account account = accountService.getAccount(id);
        Long userId = (account.getUser() != null) ? account.getUser().getId() : null;

        return new AccountResponse(
                account.getAccountId(),
                userId,
                account.getName(),
                account.getBalance()
        );
    }
    @Operation(
            summary = "Deposit money",
            description = "Deposits a specified amount into an account"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Deposit successful"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "400", description = "Invalid amount")
    })
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
