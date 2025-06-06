package com.example.pointsystem.controller;

import com.example.pointsystem.model.User;
import com.example.pointsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Optional<User> found = userRepository.findByUsername(user.getUsername());
        if (found.isPresent() && found.get().getPassword().equals(user.getPassword())) {
            Map<String, Object> response = new HashMap<>();
            response.put("username", found.get().getUsername());
            response.put("points", found.get().getPoints());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("이미 존재하는 사용자입니다.");
        }
        user.setPoints(0);
        userRepository.save(user);

        Map<String, Object> response = new HashMap<>();
        response.put("username", user.getUsername());
        response.put("points", user.getPoints());
        return ResponseEntity.ok(response);
    }
}
