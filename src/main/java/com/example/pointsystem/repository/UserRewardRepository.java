package com.example.pointsystem.repository;

import com.example.pointsystem.model.UserReward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface UserRewardRepository extends JpaRepository<UserReward, Long> {
    boolean existsByUsernameAndRewardIdAndPurchasedAtAfter(String username, Long rewardId, LocalDateTime after);
}
