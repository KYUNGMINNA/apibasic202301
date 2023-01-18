package com.example.apibasic.jpa_relation.entity;

import lombok.*;

import javax.persistence.*;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(of="empId")
@Builder
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long empId; //사원번호

    private String empName; //사원이름


    /*
        EAGER : 항상 조인을 하도록 설정  -- **default로 설정되어 있음 **
        -->기본키 값만 알고 싶은데도 , 테이블 자체를 조인함
        LAZY : 부서 정보가 필요 할 때만 조인 : 실무에서는 ManyToOne시 무조건 LAZY설정
        -->필요 없을 때는 조인안함! -->성능적 이점이 있음 .
        **ManyToOne은 LAZY로 사용하자 **
     */
    @ManyToOne(fetch = FetchType.LAZY) // Employee - M   : Department - One  -->연관관계 매핑
    @JoinColumn(name = "dept_id") //FK컬럼 이름 지정 : 내 테이블에도 dept_id로 컬럼명 적용
    private Department department; //부서
    // 실제 DB에  객체 부분 적용될 때는 부서코드 (PK)컬럼만 들어 감 .
    //Employee 테이블에만  Department 테이블 기본키가 있는 단방향 매핑 상황
    //Department 테이블에는 Employee 테이블 정보 하나도 없음

}
