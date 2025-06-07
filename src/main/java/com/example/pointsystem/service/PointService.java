package com.example.pointsystem.service;

import com.example.pointsystem.model.User; // User 모델 임포트
import com.example.pointsystem.repository.UserRepository; // UserRepository 임포트
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PointService {

    private final UserRepository userRepository;

    public PointService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 사용자에게 포인트를 추가합니다.
     * @param userId 포인트를 추가할 사용자의 ID
     * @param amount 추가할 포인트 양
     */
    @Transactional
    public void addPoints(Long userId, long amount) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("포인트를 추가할 사용자를 찾을 수 없습니다."));
        user.setPoints(user.getPoints() + (int) amount); // int로 캐스팅
        userRepository.save(user);
        System.out.println("사용자 " + user.getUsername() + "에게 " + amount + " 포인트 추가됨. 현재 포인트: " + user.getPoints());
    }

    /**
     * 사용자에게서 포인트를 차감합니다.
     * @param userId 포인트를 차감할 사용자의 ID
     * @param amount 차감할 포인트 양
     * @throws IllegalArgumentException 포인트가 부족하거나 사용자를 찾을 수 없을 때 발생
     */
    @Transactional
    public void deductPoints(Long userId, int amount) { // int amount로 변경 (보상 비용이 int이므로)
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("포인트를 차감할 사용자를 찾을 수 없습니다."));

        if (user.getPoints() < amount) {
            throw new IllegalArgumentException("포인트가 부족합니다. 현재 " + user.getPoints() + "점, 필요 " + amount + "점");
        }

        user.setPoints(user.getPoints() - amount);
        userRepository.save(user);
        System.out.println("사용자 " + user.getUsername() + "에게서 " + amount + " 포인트 차감됨. 현재 포인트: " + user.getPoints());
    }

    // 사용자 이름으로 포인트를 조회하는 메서드 (MainPage.jsx의 fetchPoints에서 사용)
    public int getPointsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + username));
        return user.getPoints();
    }
}