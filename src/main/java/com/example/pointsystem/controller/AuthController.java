package com.example.pointsystem.controller;

import com.example.pointsystem.model.User;
import com.example.pointsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "https://javateam2-front.onrender.com", allowCredentials = "true")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // ✅ 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            Optional<User> found = userRepository.findByUsername(user.getUsername());

            if (found.isPresent() && found.get().getPassword().equals(user.getPassword())) {
                Map<String, Object> response = new HashMap<>();
                response.put("username", found.get().getUsername());
                response.put("points", found.get().getPoints());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(401).body("아이디 또는 비밀번호가 올바르지 않습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace(); // 콘솔 출력
            return ResponseEntity.status(500).body("로그인 중 서버 오류 발생");
        }
    }

    // ✅ 회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        try {
            Optional<User> existing = userRepository.findByUsername(user.getUsername());
            if (existing.isPresent()) {
                return ResponseEntity.badRequest().body("이미 존재하는 사용자입니다.");
            }

            user.setPoints(0);
            userRepository.save(user);

            Map<String, Object> response = new HashMap<>();
            response.put("username", user.getUsername());
            response.put("points", user.getPoints());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("회원가입 중 서버 오류 발생");
        }
    }
}
