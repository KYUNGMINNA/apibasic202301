package com.example.apibasic.post.dto;


import com.example.apibasic.post.entity.HashTagEntity;
import com.example.apibasic.post.entity.PostEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.stream.Collectors;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
@Builder
public class PostResponseDTO {

    private String author;
    private String title;
    private String content;
    private List<String> hashTags;

    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDateTime regDate;


    //엔터티를 DTO로 변환하는 생성자
    public PostResponseDTO(PostEntity entity){
        this.author=entity.getWriter();
        this.content=entity.getContent();
        this.title=entity.getTitle();
        this.regDate=entity.getCreateDate();
        this.hashTags=entity.getHashTags()
                        .stream()
                        .map(hte->hte.getTagName())
                        .collect(Collectors.toList());
    }


}
