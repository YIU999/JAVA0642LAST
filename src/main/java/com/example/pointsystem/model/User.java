package com.example.pointsystem.model;

import jakarta.persistence.*; // Spring Boot 3 이상에서는 'javax.persistence' 대신 'jakarta.persistence'를 사용합니다.
import lombok.AllArgsConstructor; // 모든 필드를 인자로 받는 생성자를 자동으로 생성합니다.
import lombok.Getter;           // 모든 필드에 대한 Getter 메서드를 자동으로 생성합니다.
import lombok.NoArgsConstructor;    // 인자가 없는 기본 생성자를 자동으로 생성합니다.
import lombok.Setter;           // 모든 필드에 대한 Setter 메서드를 자동으로 생성합니다.

/**
 * 사용자(User) 엔티티 클래스.
 * 애플리케이션의 사용자 정보를 나타내며, 데이터베이스의 'users' 테이블과 매핑됩니다.
 */
@Entity // 이 클래스가 JPA 엔티티임을 나타냅니다.
@Getter // Lombok: 모든 필드에 대한 Getter 메서드를 자동으로 생성합니다.
@Setter // Lombok: 모든 필드에 대한 Setter 메서드를 자동으로 생성합니다.
@NoArgsConstructor // Lombok: 인자가 없는 기본 생성자를 자동으로 생성합니다. (JPA에서 엔티티 생성 시 필요)
@AllArgsConstructor // Lombok: 모든 필드를 인자로 받는 생성자를 자동으로 생성합니다.
@Table(name = "users") // 매핑될 데이터베이스 테이블의 이름을 'users'로 명시합니다.
public class User {

    @Id // 이 필드가 엔티티의 기본 키(Primary Key)임을 나타냅니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키의 자동 생성 전략을 지정합니다. IDENTITY는 데이터베이스가 ID를 자동으로 생성하도록 위임합니다.
    private Long id; // 사용자의 고유 식별자

    @Column(nullable = false, unique = true) // 이 필드가 데이터베이스 컬럼에 매핑되며, NULL을 허용하지 않고 유니크해야 합니다.
    private String username; // 사용자 아이디 (로그인에 사용)

    @Column(nullable = false) // 이 필드가 데이터베이스 컬럼에 매핑되며, NULL을 허용하지 않습니다.
    private String password; // 사용자 비밀번호 (실제 운영 환경에서는 반드시 암호화하여 저장해야 합니다)

    private int points; // 사용자가 보유한 포인트

    // 추가적인 생성자 (username, password만 받아 User 객체를 생성하고, points를 0으로 초기화)
    // @AllArgsConstructor가 있으면 이 생성자는 선택 사항입니다.
    // 하지만 명시적으로 points 초기화를 보여주기 위해 남겨둡니다.
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.points = 0; // 새로운 사용자 생성 시 기본 포인트는 0으로 설정
    }
}