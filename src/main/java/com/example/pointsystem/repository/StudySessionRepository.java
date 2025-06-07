package com.example.pointsystem.repository;

import com.example.pointsystem.model.StudySession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudySessionRepository extends JpaRepository<StudySession, Long> {
    Optional<StudySession> findByUsernameAndEndTimeIsNull(String username);
}
