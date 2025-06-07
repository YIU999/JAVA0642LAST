package com.example.pointsystem.model;

// JPA 어노테이션들을 임포트합니다.

import jakarta.persistence.*;

import java.time.LocalDateTime; // Java 8의 날짜와 시간을 다루는 클래스를 임포트합니다.

/**
 * 공부 세션(StudySession) 엔티티 클래스.
 * 사용자의 공부 시작 및 종료 시간을 기록하여 데이터베이스에 저장합니다.
 * 'study_sessions' 테이블과 매핑됩니다.
 */
@Entity // 이 클래스가 JPA 엔티티임을 나타냅니다. 데이터베이스 테이블과 매핑됩니다.
@Table(name = "study_sessions") // 매핑될 데이터베이스 테이블의 이름을 'study_sessions'로 명시합니다.
public class StudySession {

    @Id // 이 필드가 엔티티의 기본 키(Primary Key)임을 나타냅니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키의 자동 생성 전략을 지정합니다. IDENTITY는 데이터베이스가 ID를 자동으로 생성하도록 위임합니다.
    private Long id; // 공부 세션의 고유 식별자 (ID)

    @Column(nullable = false) // 이 필드가 데이터베이스 컬럼에 매핑되며, NULL 값을 허용하지 않습니다.
    private String username; // 공부 세션을 시작한 사용자의 아이디 (로그인 아이디와 연결)

    @Column(name = "start_time", nullable = false) // 'start_time'이라는 컬럼 이름으로 매핑되며, NULL 값을 허용하지 않습니다.
    private LocalDateTime startTime; // 공부 세션의 시작 시간

    @Column(name = "end_time") // 'end_time'이라는 컬럼 이름으로 매핑되며, NULL 값을 허용합니다. (아직 종료되지 않은 세션은 NULL일 수 있습니다.)
    private LocalDateTime endTime; // 공부 세션의 종료 시간

    // 필요하다면 여기에 추가적인 필드를 정의할 수 있습니다. 예를 들어, 공부 카테고리 등
    // @Column
    // private String category;

    // --- 기본 생성자 (JPA에서 엔티티를 생성할 때 필요합니다) ---
    public StudySession() {
        // 기본 생성자는 JPA가 엔티티 객체를 생성할 때 사용합니다.
    }

    // --- Getter와 Setter 메서드 ---
    // 각 필드의 값을 읽고 설정하기 위한 표준 JavaBean 규약에 따른 메서드들입니다.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    // --- toString() 메서드 (선택 사항이지만 디버깅에 유용합니다) ---
    @Override
    public String toString() {
        return "StudySession{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}