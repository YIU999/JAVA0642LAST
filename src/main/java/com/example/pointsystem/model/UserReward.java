package com.example.pointsystem.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity // 이 클래스가 JPA 엔티티임을 나타냅니다.
@Table(name = "user_rewards") // 데이터베이스 테이블 이름을 'user_rewards'로 명시합니다.
public class UserReward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 구매 기록의 고유 식별자

    @Column(nullable = false)
    private String username; // 보상을 구매한 사용자의 아이디

    @Column(nullable = false)
    private String rewardName; // 구매한 보상의 이름

    @Column(name = "purchase_date", nullable = false)
    private LocalDateTime purchaseDate; // 보상을 구매한 일시

    // --- 기본 생성자 ---
    public UserReward() {
    }

    // --- Getter와 Setter 메서드 ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRewardName() {
        return rewardName;
    }

    public void setRewardName(String rewardName) {
        this.rewardName = rewardName;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    @Override
    public String toString() {
        return "UserReward{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", rewardName='" + rewardName + '\'' +
                ", purchaseDate=" + purchaseDate +
                '}';
    }
}