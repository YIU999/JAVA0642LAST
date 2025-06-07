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

    /**
     * 모든 보상 목록을 조회하는 API 엔드포인트.
     * GET /api/store/rewards
     * @return 보상 목록
     */
    @GetMapping("/rewards")
    public ResponseEntity<List<Reward>> getAll() {
        List<Reward> rewards = shopService.getAllRewards();
        return ResponseEntity.ok(rewards); // 200 OK와 함께 보상 목록 반환
    }

    /**
     * 보상 구매를 처리하는 API 엔드포인트.
     * POST /api/store/buy
     * @param body 요청 본문 (username, rewardId 포함)
     * @return 성공 메시지 또는 오류 메시지
     */
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
            // 구매 성공 시 프론트엔드에서 사용할 보상 이름과 메시지 반환
            return ResponseEntity.ok(Map.of("message", "보상 구매 성공!", "rewardName", purchasedRewardName));
        } catch (IllegalArgumentException e) {
            // PointService의 예외나 ShopService의 포인트 부족 예외를 여기서 처리
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage())); // 400 Bad Request
        } catch (Exception e) {
            // 그 외 예상치 못한 서버 내부 오류
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "보상 구매 중 서버 오류가 발생했습니다: " + e.getMessage())); // 500 Internal Server Error
        }
    }
}