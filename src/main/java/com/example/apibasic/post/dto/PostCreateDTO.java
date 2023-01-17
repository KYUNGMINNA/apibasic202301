package com.example.apibasic.post.dto;

import com.example.apibasic.post.entity.PostEntity;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
@Builder
public class PostCreateDTO {

    /*
        NotNull : null값일 경우 에러발생
        NotEmpty: 빈문자열일 경우 에러발생
        NotBlank:  null이거나 빈문자열일 경우 에러발생


        Szie ( min=1, max=10)  == Min(1) , Max(10) 같은 기능
     */

    @NotBlank
    @Size(min = 2,max = 5) // 글자수는 2~5자 사이
    private String writer;
    @NotBlank

    //@Min(1) @Max(20) -- number 타입만
    @Size(min = 0,max = 50)
    private String title;
    private String content;
    private List<String> hashTags;

    //PostEntity로 변한하는 유틸 메서드
    public PostEntity toEntity(){
        return  PostEntity.builder()
                //.postNo(PostEntity.sequence++)
                //.hashTags(this.hashTags)
                //.createDate(LocalDateTime.now())
                .writer(this.writer)
                .content(this.content)
                .title(this.title)
                .build();
    }


}
