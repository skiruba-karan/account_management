package com.example.account_management_system.repository;

import com.example.account_management_system.dto.LoanSummaryDTO;
import com.example.account_management_system.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan,Long> {
    List<Loan> findByAccount_AccountId(Long accountId);

    @Query("SELECT new LoanSummaryDTO(COUNT(1), COALESCE(SUM(l.amount),0)) FROM Loan l WHERE l.active = true")
    LoanSummaryDTO getLoanSummary();

}

