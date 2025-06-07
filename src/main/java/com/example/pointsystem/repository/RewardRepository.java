package com.example.pointsystem.repository;

import com.example.pointsystem.model.Reward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // 이 인터페이스가 데이터 접근 계층임을 명시합니다. (JpaRepository 상속 시 생략 가능)
public interface RewardRepository extends JpaRepository<Reward, Long> {
    // JpaRepository를 상속받아 기본적인 CRUD(생성, 읽기, 업데이트, 삭제) 기능을 자동으로 제공받습니다.
    // 추가적인 쿼리 메서드가 필요하다면 여기에 정의합니다.
}