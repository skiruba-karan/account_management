package com.example.account_management_system.repository;

import com.example.account_management_system.model.ATMCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ATMCardRepository extends JpaRepository<ATMCard, Long> {

    Optional<ATMCard> findByCardNumber(String cardNumber);

    boolean existsByCardNumber(String cardNumber);

    boolean existsByAccountAccountId(Long accountId);


}
