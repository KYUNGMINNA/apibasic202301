package com.example.apibasic.jpabasic.repository;

import com.example.apibasic.jpabasic.entity.Gender;
import com.example.apibasic.jpabasic.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//JPA로 CRUD Operation을 하려면 JpaRepository 인터페이스를 상속
//제너릭타입으로 첫 번째로 CRUD할 엔터티클래스의 타입
// 두 번째로 해당 엔티티의 ID(=PK)의 타입

public interface MemberRepository extends JpaRepository<MemberEntity,Long> {

    //쿼리 메서드 사용 : 메서드 이름이 규칙이 정해져 있다.
    List<MemberEntity> findByGender(Gender gender);

    List<MemberEntity> findByAccountAndGender(String account, Gender gender);

    List<MemberEntity> findByNickNameContaining(String nickName);


    //JPQL 사용 : 메서드 이름 규칙 없음
    //select 별칭 from 엔터티클래스명 as 별칭 where 별칭.필드명
    // DB테이블명아님 !!!!
    //native -sql : select m.user_code from tbl_member as m
    //jpql : select m.userId from MemberEntity as m
                    //필드명을 써라   //Entity클래스명


    // 계정명으로 회원 조회  : 같은 기능을 함
    //@Query("select m from MemberEntity as m where m.account=?1")
    @Query("select m from MemberEntity as m where m.account=:acc")
    MemberEntity getMemberByAccount(String acc);

    //닉네임과 성별 동시만족 조건으로 회원 조회
    //?1 ?2 는 매개변수의 위치를 기반으로 찾음 (즉, 첫번째 두번째) :순번 방식
    // :문자 , :문자           ::문자 방식 ( 순번이  중요하지 않음 !!) --- 많이 사용 됨
    //@Query("select m from MemberEntity m where m.nickName=?1 and m.gender=?2")
    @Query("select m from MemberEntity m where m.nickName=:nick and m.gender=:gen")
    List<MemberEntity> getMembersByNickNameAndGender(String nick,Gender gen);

    @Query("select m from MemberEntity m where m.nickName like %:nick%")
    List<MemberEntity> getMembersNickName(String nick);


}
