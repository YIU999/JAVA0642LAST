package com.example.pointsystem.controller;

import com.example.pointsystem.repository.UserRepository;
import com.example.pointsystem.model.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/points")
@CrossOrigin
public class PointController {

    private final UserRepository userRepo;

    public PointController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/{username}")
    public int getPoints(@PathVariable String username) {
        User user = userRepo.findByUsername(username).orElseThrow();
        return user.getPoints();
    }
}
