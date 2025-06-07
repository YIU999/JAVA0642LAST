package com.example.pointsystem.service;

import com.example.pointsystem.model.StudySession;
import com.example.pointsystem.repository.StudySessionRepository;
import com.example.pointsystem.repository.UserRepository;
import com.example.pointsystem.model.User;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class StudyService {
    private final StudySessionRepository sessionRepo;
    private final UserRepository userRepo;

    public StudyService(StudySessionRepository sessionRepo, UserRepository userRepo) {
        this.sessionRepo = sessionRepo;
        this.userRepo = userRepo;
    }

    public void recordStudy(String username, String category, String start, String end) {
        StudySession session = new StudySession();
        session.setUsername(username);
        session.setCategory(category);
        session.setUsername(String.valueOf(String.valueOf(LocalDateTime.parse (start))));
        session.setEndTime(LocalDateTime.parse(end));
        sessionRepo.save(session);

        long minutes = Duration.between(session.getStartTime(), session.getEndTime()).toMinutes();
        int earned = (int) minutes;

        userRepo.findByUsername(username).ifPresent(user -> {
            user.setPoints(user.getPoints() + earned);
            userRepo.save(user);
        });
    }

    public void startStudySession(String username) {

    }
}
