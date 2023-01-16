package com.example.apibasic.post.repository;

import com.example.apibasic.post.entity.PostEntity;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;


    @BeforeEach
    void bulkInsert(){
        PostEntity entity = PostEntity.builder()
                .title("제목1")
                .writer("작가1")
                .content("내용1")
                .build();
        PostEntity entity1 = PostEntity.builder()
                .title("제목2")
                .writer("작가2")
                .content("내용2")
                .build();
        PostEntity entity2 = PostEntity.builder()
                .title("제목3")
                .writer("작가3")
                .content("내용3")
                .build();
        postRepository.save(entity);
        postRepository.save(entity1);
        postRepository.save(entity2);
    }


    @Test
    @DisplayName("게시글 등록 ")
    @Transactional
    @Rollback
    void insertTest(){
        PostEntity entity = PostEntity.builder()
                .title("처음 등록")
                .writer("작가")
                .content("내용")
                .build();
        postRepository.save(entity);

        int size = postRepository.findAll().size();

        Assertions.assertEquals(4,size);
    }

    @Test
    @DisplayName("게시글 전체 조회 ")
    @Transactional
    @Rollback
    void AllTest(){
        //given

        //when
        List<PostEntity> postEntityList = postRepository.findAll();


        //then
        assertEquals(3,postEntityList.size());
    }

    @Test
    @DisplayName("게시글 한개 조회 ")
    @Transactional
    @Rollback
    void selectOneTest(){
        //given
        Long postNo=2L;
        String title="제목2";
        String writer="작가2";

        //when
        Optional<PostEntity> postEntity = postRepository.findById(postNo);
        PostEntity entity = postEntity.get();

        //then
        assertEquals(title,entity.getTitle());
        assertEquals(writer,entity.getWriter());
    }


    @Test
    @DisplayName("게시글 수정 ")
    @Transactional
    @Rollback
    void modifyTest(){
        //gvien
        Long postNo=2L;
        String newTitle="수정된제목222";
        String newWriter="수정된작가222";

        //when
        Optional<PostEntity> optional = postRepository.findById(postNo);
        optional.ifPresent(d->{
            d.setTitle(newTitle);
            d.setWriter(newWriter);
            postRepository.save(d);
        });
        Optional<PostEntity> optional1 = postRepository.findById(postNo);
        PostEntity entity = optional1.get();

        //tehn
        assertEquals(newTitle,entity.getTitle());
        assertEquals(newWriter,entity.getWriter());
    }


    @Test
    @DisplayName("게시글 한개  삭제 ")
    @Transactional
    @Rollback
    void deleteTest(){
        //given
        Long postNo=3L;

        //when
        postRepository.deleteById(postNo);
        List<PostEntity> postEntityList = postRepository.findAll();

        //then
        assertEquals(2,postEntityList.size());


    }


}