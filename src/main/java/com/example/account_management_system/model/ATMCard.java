package com.example.account_management_system.model;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Table(name="atm_card", uniqueConstraints = {
        @UniqueConstraint(name = "uk_atm_card_card_number",columnNames = "card_number")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ATMCard{

    @Id
    @Column(name = "account_id")
    private Long accountId;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "account_id",foreignKey = @ForeignKey(name = "fk_atm_card_account"))
    private Account account;

    @Column(name = "card_number",nullable = false, length = 19)
    private String cardNumber;

    @Column(name = "pin", nullable= false, length = 64)
    private String pin;

    @Column(name = "pin_attempts",nullable=false)
    private int pinAttempts = 0;

    @Column(name = "blocked",nullable = false)
    private boolean blocked = false;

    @Column(name = "daily_withdrawn", nullable = false)
    private double dailyWithdrawn = 0.0;

    @Column(name = "last_withdrawal_date")
    private LocalDate lastWithdrawalDate;

    public ATMCard(Account account, String cardNumber, String pin){
        this.account = account;
        this.cardNumber = cardNumber;
        this.pin = pin;
    }

}