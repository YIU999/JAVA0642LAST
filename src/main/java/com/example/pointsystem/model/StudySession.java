package com.example.pointsystem.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;

@Entity
public class StudySession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String category; // "baekjoon", "license" 등

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public void setUsername(String username) {

    }

    public void setCategory(String category) {

    }

    public void setEndTime(LocalDateTime parse) {

    }

    public Temporal getStartTime() {
                return null;
    }

    public Temporal getEndTime() {
            return null;
    }

    // getters, setters
}
