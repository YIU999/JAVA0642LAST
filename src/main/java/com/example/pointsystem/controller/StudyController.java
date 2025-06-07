package com.example.pointsystem.controller;

import com.example.pointsystem.service.StudyService;
import org.springframework.http.HttpStatus; // HttpStatus 임포트 추가
import org.springframework.http.ResponseEntity; // ResponseEntity 임포트 추가
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController // 이 클래스가 RESTful 웹 서비스의 컨트롤러임을 나타냅니다.
@RequestMapping("/api/study") // 이 컨트롤러의 모든 핸들러 메서드는 '/api/study' 경로 아래에 매핑됩니다.
@CrossOrigin // CORS(Cross-Origin Resource Sharing) 문제를 해결하기 위해 모든 도메인에서의 요청을 허용합니다. (개발 시 편리, 운영 시 특정 도메인으로 제한 권장)
public class StudyController {

    private final StudyService studyService; // StudyService 인스턴스를 주입받기 위한 final 필드

    // StudyService를 주입받는 생성자 (Spring의 의존성 주입)
    public StudyController(StudyService studyService) {
        this.studyService = studyService;
    }

    /**
     * 공부 시작 시간을 기록하는 API 엔드포인트.
     * 프론트엔드에서 POST /api/study/start 요청을 받습니다.
     *
     * @param body 요청 본문 (JSON 형태) - username 필드를 포함해야 합니다.
     * 예: {"username": "user123"}
     * @return 성공 시 200 OK 응답
     */
    @PostMapping("/start") // HTTP POST 요청을 '/api/study/start' 경로에 매핑합니다.
    public ResponseEntity<String> startStudy(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        if (username == null || username.isEmpty()) {
            return new ResponseEntity<>("Username is required", HttpStatus.BAD_REQUEST);
        }
        try {
            studyService.startStudySession(username); // StudyService에 startStudySession 메서드 추가 필요
            return new ResponseEntity<>("Study session started for " + username, HttpStatus.OK);
        } catch (Exception e) {
            // 실제 서비스 계층에서 더 구체적인 예외 처리를 해야 합니다.
            return new ResponseEntity<>("Failed to start study session: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 공부 종료 시간을 기록하고, 공부 시간 계산 및 포인트 적립을 처리하는 API 엔드포인트.
     * 프론트엔드에서 POST /api/study/end 요청을 받습니다.
     *
     * @param body 요청 본문 (JSON 형태) - username 필드를 포함해야 합니다.
     * 예: {"username": "user123"}
     * @return 성공 시 200 OK 응답
     */
    @PostMapping("/end") // HTTP POST 요청을 '/api/study/end' 경로에 매핑합니다. (이전 /stop에서 변경)
    public ResponseEntity<String> endStudy(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        // category, startTime, endTime은 이 메서드에서는 필요하지 않거나,
        // startStudySession에서 시작된 세션 ID를 통해 관리하는 것이 더 일반적입니다.
        // 현재 로직이 recordStudy라면, 해당 서비스 메서드가 어떻게 동작하는지 확인 필요
        // 만약 이 엔드포인트에서 시작된 세션을 찾아 종료하는 방식이라면, username만으로도 가능
        // 혹은 클라이언트에서 세션 ID를 넘겨줘야 할 수도 있습니다.

        if (username == null || username.isEmpty()) {
            return new ResponseEntity<>("Username is required", HttpStatus.BAD_REQUEST);
        }

        try {
            // 이 부분은 studyService의 구현에 따라 달라집니다.
            // 기존 recordStudy 메서드를 사용한다면, startTime, endTime은 프론트에서 받아야 합니다.
            // 하지만 일반적인 "공부 종료"는 startStudySession이 시작한 세션을 찾는 방식입니다.
            // 예시: studyService.endStudySession(username);
            // 만약 기존 recordStudy를 사용해야 한다면, 프론트에서 startTime, endTime도 받아야 합니다.

            // 임시로 기존 recordStudy 호출 (만약 이 방식이 맞는다면)
            // 실제 로직에 따라 파라미터는 프론트엔드에서 보내줘야 함.
            // 현재 프론트엔드 endStudy에서는 username만 보내고 있으므로, 백엔드 로직 수정 필요.
            // studyService.recordStudy(
            //         body.get("username"),
            //         body.get("category"),
            //         body.get("startTime"),
            //         body.get("endTime")
            // );

            // 더 일반적인 방식: startStudySession이 시작한 세션을 찾아 종료
            studyService.endStudySession(username); // StudyService에 endStudySession 메서드 추가 필요

            return new ResponseEntity<>("Study session ended for " + username + " and points recorded.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to end study session: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}