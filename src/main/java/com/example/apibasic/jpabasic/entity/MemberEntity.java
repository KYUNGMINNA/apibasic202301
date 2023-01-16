package com.example.apibasic.jpabasic.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;


@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="userId")//userId 값으로 비교 해봤을 때 같으면 같은 것
@Builder
// JPA
@Entity // JPA의 Entity객체 : DB와 연동되는 객체
@Table(name = "tbl_member") //테이블 이름 지정
public class MemberEntity {

    @Id // 기본키 설정             //IDENTITY : mariadb or mysql  : SEQUENCE : oracle
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 생성 전략
    @Column(name = "user_code") //컬럼 이름 지정
    private Long userId; // 회원 식별 코드 (기본키)

    @Column(nullable = false,unique = true,length =30)
    // NOT NULL 제약 조건 설정, 중복 제약조건 설정 .byte=30으로 설정
    private String account; // 계정명

    @Column(nullable = false)
    private String password; // 패스워드

    @Column( name = "user_nick",nullable = false)
    private String nickName; // 닉네임

    //Enum 은  @Enumerated(EnumType.ORDINAL) 가 default 적용되어있음
    // 순번이 아닌 String으로 쓰려면  ORDINAL ->STRING으로 수정 해야 함
    @Enumerated(EnumType.STRING) // ORDINAL : 순번(0 부터 시작) , STRING : 순수 문자열
    private Gender gender; // 성별

    @CreationTimestamp //INSERTS 시점에 서버 시간을 자동으로 입력
    private LocalDateTime joinDate; // 가입일자와 시간

    @UpdateTimestamp //UPDATE 시점에 서버 시간을 자동으로 입력
    private LocalDateTime modifyDate; // 정보 수정 시간
}