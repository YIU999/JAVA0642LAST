package com.example.pointsystem.service;

import com.example.pointsystem.model.StudySession; // StudySession 모델 클래스 임포트 (모델 패키지에 있어야 함)
import com.example.pointsystem.model.User; // User 모델 클래스 임포트 (모델 패키지에 있어야 함)
import com.example.pointsystem.repository.StudySessionRepository; // StudySessionRepository 임포트 (repository 패키지에 있어야 함)
import com.example.pointsystem.repository.UserRepository; // UserRepository 임포트 (repository 패키지에 있어야 함)
import org.springframework.stereotype.Service; // @Service 어노테이션 임포트
import org.springframework.transaction.annotation.Transactional; // 트랜잭션 관리를 위한 임포트

import java.time.LocalDateTime; // 시간 관련 클래스 임포트
import java.util.Optional; // Optional 임포트

@Service // 이 클래스는 서비스 계층의 컴포넌트임을 나타냅니다.
public class StudyService {

    private final StudySessionRepository studySessionRepository;
    private final UserRepository userRepository; // 사용자 정보를 얻기 위해 필요
    private final PointService pointService; // 포인트 적립을 위해 필요

    // 생성자 주입
    public StudyService(StudySessionRepository studySessionRepository, UserRepository userRepository, PointService pointService) {
        this.studySessionRepository = studySessionRepository;
        this.userRepository = userRepository;
        this.pointService = pointService;
    }

    /**
     * 공부 세션을 시작하고 시작 시간을 기록합니다.
     * @param username 공부를 시작하는 사용자의 아이디
     * @throws IllegalStateException 이미 진행 중인 세션이 있을 경우 발생
     */
    @Transactional // 이 메서드 내의 모든 데이터베이스 작업이 하나의 트랜잭션으로 처리되도록 합니다.
    public void startStudySession(String username) {
        // 이미 진행 중인 공부 세션이 있는지 확인 (선택 사항이지만 좋은 패턴)
        Optional<StudySession> activeSession = studySessionRepository.findByUsernameAndEndTimeIsNull(username);
        if (activeSession.isPresent()) {
            throw new IllegalStateException("이미 진행 중인 공부 세션이 있습니다. 이전 세션을 먼저 종료해주세요.");
        }

        StudySession session = new StudySession();
        session.setUsername(username);
        session.setStartTime(LocalDateTime.now()); // 현재 시간을 시작 시간으로 설정
        // StudySession 엔티티에 'category' 필드가 있다면 여기서 설정 가능
        // session.setCategory(category);
        studySessionRepository.save(session); // 데이터베이스에 저장
        System.out.println(username + "님의 공부 세션이 시작되었습니다: " + session.getStartTime());
    }

    /**
     * 진행 중인 공부 세션을 종료하고, 공부 시간 계산 및 포인트 적립을 처리합니다.
     * @param username 공부를 종료하는 사용자의 아이디
     * @throws IllegalArgumentException 시작된 세션이 없을 경우 발생
     */
    @Transactional
    public void endStudySession(String username) {
        // 사용자의 가장 최근 시작된 (아직 종료되지 않은) 공부 세션을 찾습니다.
        Optional<StudySession> activeSession = studySessionRepository.findByUsernameAndEndTimeIsNull(username);

        if (activeSession.isEmpty()) {
            throw new IllegalArgumentException("시작된 공부 세션이 없습니다. 공부 시작 버튼을 먼저 눌러주세요.");
        }

        StudySession session = activeSession.get();
        session.setEndTime(LocalDateTime.now()); // 현재 시간을 종료 시간으로 설정
        studySessionRepository.save(session); // 변경된 세션 정보 저장

        // 공부 시간 계산 (분 단위)
        long durationMinutes = java.time.Duration.between(session.getStartTime(), session.getEndTime()).toMinutes();
        long pointsEarned = durationMinutes / 10; // 예시: 10분당 1포인트 적립 로직

        // 포인트 적립
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("포인트를 적립할 사용자를 찾을 수 없습니다."));

        pointService.addPoints(user.getId(), pointsEarned); // PointService를 통해 포인트 추가 (이 서비스도 구현되어 있어야 함)

        System.out.println(username + "님의 공부 세션이 종료되었습니다. " + durationMinutes + "분 공부하여 " + pointsEarned + "포인트를 획득했습니다.");
    }
}