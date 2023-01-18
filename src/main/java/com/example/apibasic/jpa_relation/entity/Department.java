package com.example.apibasic.jpa_relation.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString(exclude = "employees") //무한 참조 방지
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="deptId")
@Builder
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deptId; //부서번호
    private String deptName; //부서이름

    // DB에서는 단방향 매핑만 존재하지만
    //JPA에서는 양방향 매핑을 가능함

    //양방향매핑에서는 상대방 엔터티의 정보를 수정할 수는 없고
    //단순 읽기 기능(조회)만 지원한다.
    //mappedBy 에는 상대방 엔터티의 조인되는 필드명을 씀
    //--> 현재 Employee 클래스에서 단뱡향 매핑에 사용하고 있는 필드 변수명을 적음
    //  class Employee {  @ManyToOne private Department department;
    @OneToMany(mappedBy = "department") // Department가 one
    private List<Employee> employees;


}
