package com.example.apibasic.post.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

//게시물의 데이터 자바빈즈
@Setter @Getter @ToString
@NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
@Builder
public class PostEntity2 {

    public static long sequence=1L; //연속된 일련 번호

    private Long postNo; //게시물 식별 번호
    private String writer; //작성자
    private String title; //제목
    private String content; //내용
    private List<String> hashTags; //해시테그 목록


    //LocalDateTime은  년 월 일 시 분 초
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate; //작성 시간


    private LocalDateTime modifyDate; //수정 시간




}
