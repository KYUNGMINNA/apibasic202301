package com.example.apibasic.post.repository;

import com.example.apibasic.post.dto.PageRequestDTO;
import com.example.apibasic.post.dto.PageResponseDTO;
import com.example.apibasic.post.entity.PostEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;

import java.util.List;

@SpringBootTest
class PageTest {

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    void bulkInsert() {
        for (int i = 1; i <=483; i++) {
            PostEntity post = PostEntity.builder()
                    .title("안녕~~" + i)
                    .writer("김말종" + i)
                    .content("아무말아무말아무말~~~" + i)
                    .build();
            postRepository.save(post);
        }
    }

    @Test
    void pagingTest() {

        //클라이언트가 전달한 페이지 정보
        PageRequestDTO dto = PageRequestDTO.builder()
                .page(4)
                .sizePerPage(10)
                .build();


        //페이지 정보 생성 ::최신글이 글번호가 크다라고 가정하지 마라!! 번호가 아니라 임의의 문자열일 수 도 있다.
        PageRequest pageInfo = PageRequest.of(
                dto.getPage() - 1,
                dto.getSizePerPage(),
                Sort.Direction.DESC, // 내림차 정렬
                "createDate" // 정렬 기준: 자바 필드명으로 작성
        );
        // PageRequest.of 사용 할 때 -1씩 해야 함
        // -->왜냐면  PageRequest는 1페이지를 page=0으로 보고 있음


        Page<PostEntity> postEntities = postRepository.findAll(pageInfo);

        // 실제 조회된 데이터
        List<PostEntity> contents = postEntities.getContent();

        int totalPages = postEntities.getTotalPages();
        long totalElements = postEntities.getTotalElements();

        System.out.println("contents.size() = " + contents.size());
        System.out.println("totalPages = " + totalPages);
        System.out.println("totalElements = " + totalElements);

        contents.forEach(System.out::println);



    }


    @Test
    @DisplayName("제목에 3 이 포함된 결과를 검색한 후 1페이지 정보를 조회해야 한다")
    void pageTest2() {
        //gven
        String title="3";
        PageRequest pageRequest=PageRequest.of(
                14,
                10,
                Sort.Direction.DESC,
                "createDate");

        Slice<PostEntity> postEntityPage
                = postRepository.findByTitleContaining(title, pageRequest);

        List<PostEntity> contents = postEntityPage.getContent();

        boolean next = postEntityPage.hasNext();
        boolean prev = postEntityPage.hasPrevious();
        System.out.println("prev = " + prev);
        System.out.println("next = " + next);

        contents.forEach(System.out::println);

        //페이지 정보
        PageResponseDTO<PostEntity> dto=
                new PageResponseDTO<PostEntity>((Page<PostEntity>) postEntityPage);

        System.out.println("dto = " + dto);

    }


}
