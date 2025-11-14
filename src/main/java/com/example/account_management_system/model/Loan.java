package com.example.account_management_system.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "loans")
@Getter
@Setter
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="accountId",nullable = false)
    private Account account;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private Double interestRate;

    @Column(nullable = false)
    private Integer tenureMonths;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime closedAt;

    protected Loan(){}

    public Loan(Account account, Double amount, Double interestRate, Integer tenureMonths){
        this.account = account;
        this.amount = amount;
        this.interestRate = interestRate;
        this.tenureMonths = tenureMonths;
        this.active = true;
        this.createdAt = LocalDateTime.now();
    }
    public void closeLoan(){
        this.active = false;
        this.closedAt = LocalDateTime.now();
    }

    public void setActive(boolean active){
        this.active = active;
        if(!active){
            this.closedAt = LocalDateTime.now();
        }
    }

    @PrePersist
    protected  void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof Loan loan)) return false;
        return Objects.equals(id,loan.id);
    }

    @Override
    public int hashCode(){
        return Objects.hash(id);
    }
}
