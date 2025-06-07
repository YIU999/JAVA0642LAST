package com.example.pointsystem.service;

import com.example.pointsystem.model.Reward;
import com.example.pointsystem.model.User;
import com.example.pointsystem.model.UserReward;
import com.example.pointsystem.repository.RewardRepository;
import com.example.pointsystem.repository.UserRepository;
import com.example.pointsystem.repository.UserRewardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList; // ArrayList 임포트 추가
import java.util.List;

@Service
public class ShopService {


    private final RewardRepository rewardRepository;
    private final UserRepository userRepository;
    private final PointService pointService;
    private final UserRewardRepository userRewardRepository;

    public ShopService(RewardRepository rewardRepository, UserRepository userRepository, PointService pointService, UserRewardRepository userRewardRepository) {
        this.rewardRepository = rewardRepository;
        this.userRepository = userRepository;
        this.pointService = pointService;
        this.userRewardRepository = userRewardRepository;
    }


    public List<Reward> getAllRewards() {
        List<Reward> hardcodedRewards = new ArrayList<>();


        Reward reward1 = new Reward();
        reward1.setId(1L);
        reward1.setName("게임 1시간 허용권");
        reward1.setCost(100);
        hardcodedRewards.add(reward1);

        Reward reward2 = new Reward();
        reward2.setId(2L);
        reward2.setName("좋아하는 간식 구매권");
        reward2.setCost(50);
        hardcodedRewards.add(reward2);

        Reward reward3 = new Reward();
        reward3.setId(3L);
        reward3.setName("새로운 스터디 플래너");
        reward3.setCost(150);
        hardcodedRewards.add(reward3);

        Reward reward4 = new Reward();
        reward4.setId(4L);
        reward4.setName("외식 상품권");
        reward4.setCost(300);
        hardcodedRewards.add(reward4);

        Reward reward5 = new Reward();
        reward5.setId(5L);
        reward5.setName("주말 자유 시간 1시간");
        reward5.setCost(200);
        hardcodedRewards.add(reward5);

        System.out.println("DEBUG: Hardcoded rewards returned. Size: " + hardcodedRewards.size());
        return hardcodedRewards;
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
    @Transactional
    public String buy(String username, Long rewardId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));


        Reward reward = rewardRepository.findById(rewardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 보상입니다."));

        if (user.getPoints() < reward.getCost()) {
            throw new IllegalArgumentException("포인트가 부족합니다. 현재 " + user.getPoints() + "점, 필요 " + reward.getCost() + "점");
        }

        pointService.deductPoints(user.getId(), reward.getCost());

        UserReward userReward = new UserReward();
        userReward.setUsername(username);
        userReward.setRewardName(reward.getName());
        userReward.setPurchaseDate(LocalDateTime.now());
        userRewardRepository.save(userReward);

        System.out.println(username + "님이 " + reward.getName() + " 보상을 " + reward.getCost() + " 포인트로 구매했습니다.");

        return reward.getName();
    }
}