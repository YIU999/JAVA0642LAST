package com.example.pointsystem.model;

import jakarta.persistence.*;

@Entity
public class Reward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int cost;

    public int getCost() {
        return 0;
    }

    /* getters, setters */
}
