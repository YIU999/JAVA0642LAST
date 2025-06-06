package com.example.pointsystem.service;

import com.example.pointsystem.model.Reward;
import com.example.pointsystem.model.User;
import com.example.pointsystem.model.UserReward;
import com.example.pointsystem.repository.RewardRepository;
import com.example.pointsystem.repository.UserRepository;
import com.example.pointsystem.repository.UserRewardRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShopService {
    private final RewardRepository rewardRepo;
    private final UserRepository userRepo;
    private final UserRewardRepository userRewardRepo;

    public ShopService(RewardRepository rewardRepo, UserRepository userRepo, UserRewardRepository userRewardRepo) {
        this.rewardRepo = rewardRepo;
        this.userRepo = userRepo;
        this.userRewardRepo = userRewardRepo;
    }

    public List<Reward> getAllRewards() {
        return rewardRepo.findAll();
    }

    public void buy(String username, Long rewardId) {
        Reward reward = rewardRepo.findById(rewardId).orElseThrow();
        User user = userRepo.findByUsername(username).orElseThrow();

        // 제한시간: 1시간 이내 동일 아이템 구매 제한
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);
        boolean alreadyBoughtRecently = userRewardRepo.existsByUsernameAndRewardIdAndPurchasedAtAfter(username, rewardId, oneHourAgo);
        if (alreadyBoughtRecently) throw new RuntimeException("1시간 내에 다시 구매할 수 없습니다.");

        if (user.getPoints() < reward.getCost()) throw new RuntimeException("포인트 부족");

        user.setPoints(user.getPoints() - reward.getCost());
        userRepo.save(user);

        UserReward ur = new UserReward();
        ur.setUsername(username);
        ur.setRewardId(rewardId);
        ur.setPurchasedAt(LocalDateTime.now());
        userRewardRepo.save(ur);
    }
}
