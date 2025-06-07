package com.example.pointsystem.controller;

import com.example.pointsystem.model.Reward;
import com.example.pointsystem.service.ShopService;
import org.springframework.http.HttpStatus; // HttpStatus 임포트 추가
import org.springframework.http.ResponseEntity; // ResponseEntity 임포트 추가
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/store")
@CrossOrigin // CORS 허용
public class ShopController {

    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }


    @GetMapping("/rewards")
    public ResponseEntity<List<Reward>> getAll() {
        List<Reward> rewards = shopService.getAllRewards();
        return ResponseEntity.ok(rewards); // 200 OK와 함께 보상 목록 반환
    }


    @PostMapping("/buy")
    public ResponseEntity<?> buy(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        Long rewardId = null;
        try {
            rewardId = Long.parseLong(body.get("rewardId"));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(Map.of("message", "올바른 rewardId 형식이 아닙니다."));
        }

        if (username == null || username.isEmpty() || rewardId == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "사용자 이름과 보상 ID는 필수입니다."));
        }

        try {
            String purchasedRewardName = shopService.buy(username, rewardId);

            return ResponseEntity.ok(Map.of("message", "보상 구매 성공!", "rewardName", purchasedRewardName));
        } catch (IllegalArgumentException e) {

            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage())); // 400 Bad Request
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "보상 구매 중 서버 오류가 발생했습니다: " + e.getMessage())); // 500 Internal Server Error
        }
    }
}