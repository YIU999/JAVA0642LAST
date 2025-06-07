package com.example.pointsystem.repository;

import com.example.pointsystem.model.UserReward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRewardRepository extends JpaRepository<UserReward, Long> {
    // 사용자별 구매 기록을 조회하는 등의 추가 메서드가 필요하면 여기에 정의합니다.
    // 예: List<UserReward> findByUsername(String username);
}