package com.example.pointsystem.controller;

import com.example.pointsystem.model.User;
import com.example.pointsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        User found = userRepository.findByUsername(user.getUsername());
        if (found != null && found.getPassword().equals(user.getPassword())) {
            Map<String, Object> response = new HashMap<>();
            response.put("username", found.getUsername());
            response.put("points", found.getPoints());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return ResponseEntity.badRequest().body("이미 존재하는 사용자입니다.");
        }
        user.setPoints(0); // 초기 포인트
        userRepository.save(user);
        return ResponseEntity.ok("회원가입 성공");
    }
}
