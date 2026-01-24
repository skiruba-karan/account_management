package com.example.account_management_system.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name="account")
public class Account {
    @Id
    @Column(name = "account_id")
    private Long accountId;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "account_id")
    private User user;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double balance;

    public Account(User user, String name, double balance){
        this.user = user;
        this.name = name;
        this.balance = balance;
    }

}
