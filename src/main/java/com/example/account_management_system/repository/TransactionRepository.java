package com.example.account_management_system.repository;


import com.example.account_management_system.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    @Query("SELECT t FROM Transaction t WHERE t.accountId = :accountId ORDER BY t.timestamp DESC")
    List<Transaction> findRecentTransactions(Long accountId);
    List<Transaction> findByAccountIdAndType(Long accountId, String type);

    @Query("SELECT t FROM Transaction t WHERE t.accountId = :accountId AND t.timestamp BETWEEN :start AND :end")
    List<Transaction> findByAccountIdAndTimestamp(
            @Param("accountId") Long accountId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end

            );
}
