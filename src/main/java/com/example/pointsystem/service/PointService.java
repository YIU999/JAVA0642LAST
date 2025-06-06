package com.example.pointsystem.service;

import com.example.pointsystem.model.User;
import com.example.pointsystem.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class PointService {
    private final UserRepository userRepo;

    public PointService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public int getPoints(String username) {
        return userRepo.findByUsername(username)
                .map(User::getPoints)
                .orElse(0);
    }
}
