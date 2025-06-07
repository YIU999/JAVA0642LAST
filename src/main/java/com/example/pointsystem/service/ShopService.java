package com.example.pointsystem.service;

import com.example.pointsystem.model.Reward;
import com.example.pointsystem.model.User;
import com.example.pointsystem.model.UserReward;
import com.example.pointsystem.repository.RewardRepository;
import com.example.pointsystem.repository.UserRepository; // User 모델 접근을 위해 필요
import com.example.pointsystem.repository.UserRewardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // 트랜잭션 관리를 위해 추가
import java.time.LocalDateTime;
import java.util.List;

@Service // 이 클래스는 서비스 계층의 컴포넌트임을 나타냅니다.
public class ShopService {

    private final RewardRepository rewardRepository;
    private final UserRepository userRepository; // 사용자 정보(포인트)를 위해 주입
    private final PointService pointService; // 포인트 차감/적립을 위해 주입
    private final UserRewardRepository userRewardRepository; // 구매 기록 저장을 위해 주입

    // 생성자 주입 (Spring이 자동으로 빈들을 주입해줍니다)
    public ShopService(RewardRepository rewardRepository, UserRepository userRepository, PointService pointService, UserRewardRepository userRewardRepository) {
        this.rewardRepository = rewardRepository;
        this.userRepository = userRepository;
        this.pointService = pointService;
        this.userRewardRepository = userRewardRepository;
    }

    /**
     * 모든 이용 가능한 보상 목록을 조회합니다.
     * @return 보상 목록
     */
    public List<Reward> getAllRewards() {
        return rewardRepository.findAll(); // RewardRepository를 통해 모든 보상을 가져옵니다.
    }

    /**
     * 사용자가 특정 보상을 구매하는 로직을 처리합니다.
     * 트랜잭션(@Transactional)을 사용하여 구매 과정의 원자성을 보장합니다.
     * (포인트 차감 및 구매 기록 저장이 모두 성공하거나 모두 실패)
     *
     * @param username 구매하려는 사용자의 아이디
     * @param rewardId 구매하려는 보상의 ID
     * @return 구매한 보상의 이름 (프론트엔드 알림에 사용)
     * @throws IllegalArgumentException 포인트 부족, 사용자 또는 보상을 찾을 수 없을 때 발생
     */
    @Transactional // 이 메서드 내의 모든 데이터베이스 작업이 하나의 트랜잭션으로 처리되도록 합니다.
    public String buy(String username, Long rewardId) {
        // 1. 사용자 조회
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 2. 보상 조회
        Reward reward = rewardRepository.findById(rewardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 보상입니다."));

        // 3. 포인트 확인
        if (user.getPoints() < reward.getCost()) {
            throw new IllegalArgumentException("포인트가 부족합니다. 현재 " + user.getPoints() + "점, 필요 " + reward.getCost() + "점");
        }

        // 4. 포인트 차감 (PointService를 통해)
        // PointService의 deductPoints 메서드가 User ID를 받도록 구현되어 있어야 합니다.
        pointService.deductPoints(user.getId(), reward.getCost()); // 예: User ID를 통해 포인트 차감

        // 5. 구매 기록 저장
        UserReward userReward = new UserReward();
        userReward.setUsername(username);
        userReward.setRewardName(reward.getName());
        userReward.setPurchaseDate(LocalDateTime.now());
        userRewardRepository.save(userReward); // UserRewardRepository를 통해 구매 기록 저장

        System.out.println(username + "님이 " + reward.getName() + " 보상을 " + reward.getCost() + " 포인트로 구매했습니다.");

        return reward.getName(); // 구매 성공 시 보상 이름 반환 (프론트엔드 응답에 사용)
    }
}