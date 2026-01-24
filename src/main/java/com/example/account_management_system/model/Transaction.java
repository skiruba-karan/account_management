package com.example.account_management_system.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = "account")
@Table(name="transaction")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long transactionId;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="account_id",nullable = false)
    @JsonIgnore
    private Account account;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false, unique=true, length = 30)
    private String referenceCode;

    @PrePersist
    public void prePersist(){
        if(this.timestamp == null){
            this.timestamp=LocalDateTime.now();
        }
        if(this.referenceCode == null){
            this.referenceCode = generatedReferenceCode(this.timestamp);
        }
    }

    private String generatedReferenceCode(LocalDateTime ts){
        return "TXN-"+ts.getYear()+
                            String.format("%02d",ts.getMonthValue())
                            +"-"+ UUID.randomUUID().toString().substring(0,8).toUpperCase();
    }
}
