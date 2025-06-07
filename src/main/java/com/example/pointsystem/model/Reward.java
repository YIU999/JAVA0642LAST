package com.example.pointsystem.model;

import jakarta.persistence.*;

@Entity // 이 클래스가 JPA 엔티티임을 나타냅니다.
@Table(name = "rewards") // 매핑될 데이터베이스 테이블 이름을 'rewards'로 명시합니다.
public class Reward {
    @Id // 이 필드가 엔티티의 기본 키(Primary Key)임을 나타냅니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID 자동 생성 전략 (DB에 위임)
    private Long id; // 보상의 고유 식별자

    @Column(nullable = false) // NULL 값을 허용하지 않습니다.
    private String name; // 보상의 이름 (예: "게임 허용권")

    @Column(nullable = false) // NULL 값을 허용하지 않습니다.
    private int cost; // 보상 구매에 필요한 포인트

    // --- 기본 생성자 (JPA에서 엔티티를 생성할 때 필요합니다) ---
    public Reward() {
    }

    // --- Getter와 Setter 메서드 ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Reward{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                '}';
    }
}