package com.example.pointsystem.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class UserReward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private Long rewardId;

    private LocalDateTime purchasedAt;

    public void setUsername(String username) {

    }

    public void setRewardId(Long rewardId) {

    }

    public void setPurchasedAt(LocalDateTime now) {

    }

    // getters, setters
}
