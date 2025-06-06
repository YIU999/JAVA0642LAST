package com.example.pointsystem.repository;

import com.example.pointsystem.model.StudySession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudySessionRepository extends JpaRepository<StudySession, Long> {
}
