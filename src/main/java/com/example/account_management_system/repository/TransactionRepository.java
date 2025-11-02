package com.example.account_management_system.repository;


import com.example.account_management_system.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    @Query("SELECT t FROM Transaction t WHERE t.accountId = :accountId ORDER BY t.timestamp DESC")
    List<Transaction> findRecentTransactions(Long accountId);
    List<Transaction> findByAccountIdAndType(Long accountId, String type);
}
