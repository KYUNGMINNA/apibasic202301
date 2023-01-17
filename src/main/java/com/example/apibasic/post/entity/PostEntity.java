package com.example.apibasic.post.entity;

import com.example.apibasic.post.dto.PostResponseDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

//게시물의 데이터 자바빈즈
@Setter @Getter @ToString
@NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
@Builder
@Entity
@Table(name = "tbl_post")
public class PostEntity {

    @Id // 기본키 설정             //IDENTITY : mariadb or mysql  : SEQUENCE : oracle
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 생성 전략
    @Column(name = "post_no") //생략 되어 있음
    private Long postNo; //게시물 식별 번호


    @Column(nullable = false)
    private String writer; //작성자

    @Column(nullable = false)
    private String title; //제목
    private String content; //내용


    //private List<String> hashTags; //해시테그 목록


    //LocalDateTime은  년 월 일 시 분 초
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    // Entity에서 사용하지 말고 ,DTO에서 사용하자

    //Validation 검증은 RequestDTO이런데에서 하는 것!!

    //처음 insert 할 때 create,update Timestamp적용 됨
    @CreationTimestamp
    private LocalDateTime createDate; //작성 시간

    @UpdateTimestamp
    private LocalDateTime modifyDate; //수정 시간




}
