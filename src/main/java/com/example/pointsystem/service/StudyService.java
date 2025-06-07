package com.example.pointsystem.service;

import com.example.pointsystem.model.StudySession;
import com.example.pointsystem.model.User;
import com.example.pointsystem.repository.StudySessionRepository;
import com.example.pointsystem.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class StudyService {

    private final StudySessionRepository studySessionRepository;
    private final UserRepository userRepository;
    private final PointService pointService;

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
    @Transactional
    public void startStudySession(String username) {
        Optional<StudySession> activeSession = studySessionRepository.findByUsernameAndEndTimeIsNull(username);
        if (activeSession.isPresent()) {
            throw new IllegalStateException("이미 진행 중인 공부 세션이 있습니다. 이전 세션을 먼저 종료해주세요.");
        }

        StudySession session = new StudySession();
        session.setUsername(username);
        session.setStartTime(LocalDateTime.now());
        studySessionRepository.save(session);
        System.out.println(username + "님의 공부 세션이 시작되었습니다: " + session.getStartTime());
    }

    /**
     * 진행 중인 공부 세션을 종료하고, 공부 시간 계산 및 포인트 적립을 처리합니다.
     * @param username 공부를 종료하는 사용자의 아이디
     * @throws IllegalArgumentException 시작된 세션이 없을 경우 발생
     */
    @Transactional
    public void endStudySession(String username) {
        Optional<StudySession> activeSession = studySessionRepository.findByUsernameAndEndTimeIsNull(username);

        if (activeSession.isEmpty()) {
            throw new IllegalArgumentException("시작된 공부 세션이 없습니다. 공부 시작 버튼을 먼저 눌러주세요.");
        }

        StudySession session = activeSession.get();
        session.setEndTime(LocalDateTime.now());
        studySessionRepository.save(session);

        long durationMinutes = java.time.Duration.between(session.getStartTime(), session.getEndTime()).toMinutes();
        long pointsEarned = durationMinutes / 10;

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("포인트를 적립할 사용자를 찾을 수 없습니다."));

        pointService.addPoints(user.getId(), pointsEarned);

        System.out.println(username + "님의 공부 세션이 종료되었습니다. " + durationMinutes + "분 공부하여 " + pointsEarned + "포인트를 획득했습니다.");
    }
}