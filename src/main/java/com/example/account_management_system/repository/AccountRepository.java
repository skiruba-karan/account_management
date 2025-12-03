package com.example.account_management_system.repository;

import com.example.account_management_system.dto.TopAccountDTO;
import com.example.account_management_system.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account,Long> {
    @Query("SELECT COUNT(DISTINCT a.accountId) FROM Account a")
    long countDistinctCustomers();

    @Query("SELECT COALESCE(SUM(a.balance),0) FROM Account a")
    double sumAllBalances();

    @Query("SELECT new com.example.account_management_system.dto.TopAccountDTO(a.accountId, a.balance) FROM Account a Where a.balance > :minBalance")
    List<TopAccountDTO> findTopBalanceAccounts(double minBalance);
}
