package com.example.pointsystem.model;

import jakarta.persistence.*;

@Entity
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private int balance;

    // 기본 생성자
    public BankAccount() {}

    public BankAccount(String username, int balance) {
        this.username = username;
        this.balance = balance;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public int getBalance() { return balance; }
    public void setBalance(int balance) { this.balance = balance; }
}
