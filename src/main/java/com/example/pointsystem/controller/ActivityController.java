
package com.example.pointsystem.controller;

import com.example.pointsystem.model.User;
import com.example.pointsystem.repository.UserRepository;
import com.example.pointsystem.service.BaekjoonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/activity")
public class ActivityController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BaekjoonService baekjoonService;

    @PostMapping("/solve")
    public ResponseEntity<String> solveBaekjoon(@RequestParam String username) throws IOException {
        User user = userRepository.findByUsername(username).orElseThrow();
        int count = baekjoonService.checkSolvedProblems(username);
        int earned = count * 20;
        user.setPoints(user.getPoints() + earned);
        userRepository.save(user);
        return ResponseEntity.ok("백준 연동 완료! +" + earned + "점 적립");
    }

    @PostMapping("/study")
    public ResponseEntity<String> study(@RequestParam String username, @RequestParam int minutes) {
        User user = userRepository.findByUsername(username).orElseThrow();
        int earned = (minutes / 10) * 10;
        user.setPoints(user.getPoints() + earned);
        userRepository.save(user);
        return ResponseEntity.ok("공부 시간 적립 완료! +" + earned + "점");
    }

    @PostMapping("/redeem")
    public ResponseEntity<String> redeem(@RequestParam String username, @RequestParam String reward) {
        int cost = switch (reward) {
            case "게임" -> 100;
            case "칭찬" -> 50;
            case "넷플릭스" -> 150;
            case "친구랑 놀기" -> 200;
            case "배달" -> 250;
            default -> 0;
        };
        User user = userRepository.findByUsername(username).orElseThrow();
        if (user.getPoints() < cost)
            return ResponseEntity.badRequest().body("포인트 부족");
        user.setPoints(user.getPoints() - cost);
        userRepository.save(user);
        return ResponseEntity.ok(reward + " 보상 사용 완료!");
    }
}
