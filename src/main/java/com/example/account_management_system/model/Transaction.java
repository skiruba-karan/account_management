package com.example.account_management_system.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;
    private Long accountId;
    private String type;
    private double amount;
    private LocalDateTime timestamp;
    private String referenceCode;

    @PrePersist
    public void generatedReferenceCode(){
        this.referenceCode="TXN-"+LocalDateTime.now().getYear()+
                            String.format("%02d",LocalDateTime.now().getMonthValue())
                            +"-"+ UUID.randomUUID().toString().substring(0,0).toUpperCase();
    }
}
