package com.example.pointsystem.controller;

import com.example.pointsystem.model.User;
import com.example.pointsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional; // Optional 임포트 추가

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("이미 존재하는 사용자입니다.");
        }
        user.setPoints(0);
        userRepository.save(user);
        return ResponseEntity.ok("회원가입 완료");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) { // 반환 타입을 ResponseEntity<String>으로 통일
        Optional<User> foundUser = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());

        if (foundUser.isPresent()) {
            // 로그인 성공 시 메시지 반환 (필요하다면 User 객체 정보 포함)
            // 예: return ResponseEntity.ok("로그인 성공: " + foundUser.get().getUsername());
            return ResponseEntity.ok("로그인 성공"); // 더 간단하게 "로그인 성공" 메시지만 반환
        } else {
            // 로그인 실패 시 메시지 반환
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
        }
    }
}