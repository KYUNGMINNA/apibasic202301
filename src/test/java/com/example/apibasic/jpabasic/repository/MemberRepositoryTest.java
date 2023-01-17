package com.example.apibasic.jpabasic.repository;

//junit5에서 클래스,메서드,필드 default 제한만을 허용
// public ,private 이런 제한자 사용하면안됨

import com.example.apibasic.jpabasic.entity.Gender;
import com.example.apibasic.jpabasic.entity.MemberEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.example.apibasic.jpabasic.entity.Gender.FEMALE;
import static com.example.apibasic.jpabasic.entity.Gender.MALE;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // 스프링 컨테이너를 사용해서 스프링이 관리하는 객체를 주입받는 기능 활성화

class MemberRepositoryTest {

    //스프링 빈을 주입받을 때 필드주입을 사용(junit5에서는)
    @Autowired
    MemberRepository memberRepository;

    //@BeforeEach :각 테스트 실행하기 전에 실행되는 내용
    @BeforeEach
    void bulkInsert(){
        MemberEntity saveMember1 = MemberEntity.builder()
                .account("zzz1234")
                .password("1234")
                .nickName("박꾸러긔")
                .gender(FEMALE)
                .build();
        MemberEntity saveMember2 = MemberEntity.builder()
                .account("abc4321")
                .password("4321")
                .nickName("궁예")
                .gender(MALE)
                .build();
        MemberEntity saveMember3 = MemberEntity.builder()
                .account("ppp9999")
                .password("9888")
                .nickName("찬호박")
                .gender(MALE)
                .build();
        MemberEntity saveMember4= MemberEntity.builder()
                .account("d222")
                .password("2222")
                .nickName("흥민손")
                .gender(MALE)
                .build();
        MemberEntity saveMember5 = MemberEntity.builder()
                .account("dddd123")
                .password("3333")
                .nickName("오로라")
                .gender(FEMALE)
                .build();



        memberRepository.save(saveMember1);
        memberRepository.save(saveMember2);
        memberRepository.save(saveMember3);
        memberRepository.save(saveMember4);
        memberRepository.save(saveMember5);
    }



    //테스트 메서드
    //테스트는 여러번 돌려도 성공한 테스트는 계속 성공해야 한다.
    //단언 (Assertion) : 강력히 주장한다.
    @Test
    @DisplayName("회원의 가입 정보를 데이터베이스에 저장해야 한다.")
    @Transactional
    @Rollback // 테스트가 끝나면 롤백해라.
    void saveTest() {
        // given - when - then 패턴
        // given : 테스트시 주어지는 데이터
        MemberEntity saveMember = MemberEntity.builder()
                .account("zzz1234")
                .password("1234")
                .nickName("꾸러긔")
                .gender(FEMALE)
                .build();
        // when : 실제 테스트 상황
        memberRepository.save(saveMember); // insert쿼리 실행

        Optional<MemberEntity> foundMember = memberRepository.findById(1L);// pk기반 단일 행 조회

        // then : 테스트 결과 단언
        // 회원이 조회되었을 것이다.
        MemberEntity member = foundMember.get();
        assertNotNull(member);

        // 그 저장된 회원의 user_code는 몇번? => 1번
        // param1: 내 기대값,  param2: 실제값
        //assertEquals(1L, member.getUserId());
        //-->시퀀스 키 같은 것들은 테스트에 사용하지 마라
        // --> 왜냐면 계속 변하는 값 이기 때문

        // 저장된 회원의 닉네임은 뭘까요?  => 꾸러긔
        assertEquals("꾸러긔", member.getNickName());
    }

    // 목록 조회 테스트
    @Test
    @DisplayName("회원 목록을 조회하면 3명의 회원정보가 조회되어야 한다.")
    @Transactional
    @Rollback
    void findAllTest() {
        // given

        // when


        List<MemberEntity> memberEntityList = memberRepository.findAll();

        // then
        // 조회된 리스트의 사이즈는 3이어야 한다.
        assertEquals(3, memberEntityList.size());
        // 조회된 리스트의 2번째 회원 닉네임은 궁예여야한다.
        assertEquals("궁예", memberEntityList.get(1).getNickName());

        System.out.println("\n================================");
        memberEntityList.forEach(System.out::println);
        System.out.println("==================================");

    }

    @Test
    @DisplayName("회원 데이터를 3개 등록하고 그 중 하나의 회원을 삭제해야 한다.")
    @Transactional
    @Rollback
    void deleteTest(){
        //given
        Long userCode=2L;

        //when
        memberRepository.deleteById(userCode);
        Optional<MemberEntity> foundMember = memberRepository.findById(userCode);

        //tehn
        assertFalse(foundMember.isPresent());

        assertEquals(2, memberRepository.findAll().size());
    }

    @Test
    @DisplayName("2번 회원의 닉네임과 성별을 수정해야 한다.")
    @Transactional
    @Rollback
    void modifyTest(){
        //given
        Long userCode=2L;
        String newNickName="닭강정";
        Gender newGender= FEMALE;


      /*  if (foundMember.isPresent()) {
            MemberEntity m = foundMember.get();
            m.setNickName(newNickName);
            m.setGender(newGender);
            memberRepository.save(m);
        }*/

        //when
        //JPA에서의 수정은 조회 후 setter로 변경 후 다시 save해야 됨
        Optional<MemberEntity> foundMember = memberRepository.findById(userCode);
        foundMember.ifPresent(m->{
            m.setNickName(newNickName);
            m.setGender(newGender);
            memberRepository.save(m);
        });


        Optional<MemberEntity> modifyMember = memberRepository.findById(userCode);

        //then
        assertEquals("닭강정",modifyMember.get().getNickName());
        assertEquals(FEMALE,modifyMember.get().getGender());




    }


    @Test
    @DisplayName("쿼리메서드를 사용하여 여성회원만 조회해야 한다.")
    void findByGenderTest(){
        //given
        Gender gender=FEMALE;

        //when
        List<MemberEntity> list = memberRepository.findByGender(gender);

        //then
        list.forEach(m->{
            System.out.println("m = " + m);
            assertTrue(m.getGender()==FEMALE);
        });
    }
    @Test
    @DisplayName("쿼리메서드를 사용하여 계정명이 d222이면서  남성회원만 조회해야 한다.")
    void findByAccountGenderTest(){
        //given
        Gender gender=MALE;
        String account="d222";
        //when
        List<MemberEntity> list = memberRepository.findByAccountAndGender(account,gender);

        //then
        list.forEach(m->{
            System.out.println("m = " + m);
            assertTrue(m.getGender()==MALE);
            assertEquals("d222",m.getAccount());
        });
    }

    @Test
    @DisplayName("쿼리메서드를 사용하여 이름에 '박'이 포함된 회원만 조회해야 한다")
    void findByNickNameContainingTest(){
        //given
        String nickName="박";
        //when
        List<MemberEntity> list = memberRepository.findByNickNameContaining(nickName);

        //then
        list.forEach(m->{
            System.out.println("m = " + m);
            assertTrue(m.getNickName().contains("박"));
        });
    }

    @Test
    @DisplayName("JPQL을 사용해서 계정명이 zzz1234인 회원을 조회해야 한다.")
    void jpqlTest1() {
        // given
        String account = "zzz1234";
        // when
        MemberEntity member = memberRepository.getMemberByAccount(account);
        // then
        System.out.println("member = " + member);
        assertEquals("박꾸러긔", member.getNickName());
    }
    @Test
    @DisplayName("JPQL을 사용해서 닉네임이 궁예이면서 성별이 남성인 회원을 조회해야 한다.")
    void jpqlTest2() {
        // given
        String nickName = "궁예";
        Gender gender=MALE;

        // when
        List<MemberEntity> list = memberRepository.getMembersByNickNameAndGender(nickName, gender);

        // then
        list.forEach(m -> {
            System.out.println(m);
            assertEquals("궁예", m.getNickName());
            assertEquals(MALE, m.getGender());
        });
    }
    @Test
    @DisplayName("JPQL을 사용하여 이름에 '박'이 포함된 회원만 조회해야 한다")
    void jpqlTest3(){
        //given
        String nickName="박";
        //when
        List<MemberEntity> list = memberRepository.getMembersNickName(nickName);

        //then
        list.forEach(m->{
            System.out.println("m = " + m);
            assertTrue(m.getNickName().contains("박"));
        });
    }


    @Test
    @Transactional
    @DisplayName("JPQL을 사용해서 nickName이 '꾸러긔'인 사람 삭제")
    void jpqlTest4(){
        //given
        String nickName="꾸러긔";

        memberRepository.deleteByNickName(nickName);





    }






}