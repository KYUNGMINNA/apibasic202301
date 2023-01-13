package com.example.apibasic.post.api;


import com.example.apibasic.post.dto.*;
import com.example.apibasic.post.entity.PostEntity;
import com.example.apibasic.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


//리소스 : 게시물 (Post)
/*
     게시물 목록 조회 : /posts  - GET
     게시물 개별 조회 : /posts/{id{ - GET
     게시물 등록     : /posts  - POST
     게시물 수정     : /posts/{id} - PATCH
     게시물 삭제     : /posts/{id} - DELETE
 */
@RestController
@Slf4j
@RequestMapping("/posts")
@RequiredArgsConstructor // final을 초기화 해주는 자동생성자
public class PostApiController {

    //PostRepository에게 의존하는 관계 -
    //PostRepository가 없으면 아무 기능도 못함
    //ex) repository가 주방장, controller가 서빙
    // 서빙하는 사람이 주방장을 직접 구하는 것
    //private PostRepository postRepository=new PostRepository();


    private final PostRepository postRepository;


    //@Autowired : 스프링 컨테이너에게 의존 객체를 자동 주입 해달라
    // 스프링 버전 4 이후로 생성자 단 1개면 @Autowired 생략 가능
    /*
        //의존 객체를 누군가가(스프링 컨테이너가) 주입해 주는 것
    public PostApiController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }*/



    //게시물 목록 조회
    @GetMapping   //RequestMapping이 있어 비워놔도 된다.
    public ResponseEntity<?> list(){
        log.info("/posts GET request");
        List<PostEntity> list=postRepository.findAll();

        //엔터티 리스트를 DTO리스트로 변환해서 클라이언트에 응답
        List<PostResponseDTO> responseDTOList=list.stream()
                //.map(et->new PostResponseDTO(et))
                .map(PostResponseDTO::new)
                .collect(toList());

        PostListResponseDTO listResponseDTO = PostListResponseDTO.builder()
                .count(responseDTOList.size())
                .posts(responseDTOList)
                .build();




        return  ResponseEntity
                .ok()
                .body(listResponseDTO);
    }

    //게시물 개별 조회시에는 클라이언트에게  추가로 수정시간 정보를 제공하세요

    //게시물 개별 조회
    @GetMapping("/{postNo}")
    public ResponseEntity<?> detail(
           //@PathVariable("postNo") Long No){
            @PathVariable Long postNo){
        log.info("/posts/{} GET request",postNo);

        PostEntity post = postRepository.findOne(postNo);

        //엔티티를 DTO로 변환
        PostResponseDTO postResponseDTO=new PostDetailResponseDTO(post);

        return ResponseEntity
                .ok()
                .body(postResponseDTO);
    }


    // 게시물 등록
    @PostMapping
    public ResponseEntity<?> create(@RequestBody PostCreateDTO createDTO) {
        log.info("/posts POST request");
        log.info("게시물 정보 : {}",createDTO);

        //dto를 entity로 변환 작업
        PostEntity entity=createDTO.toEntity();

        boolean flag=postRepository.save(entity);

        return flag?ResponseEntity.ok().body("INSERT-SUCCESS")
                :ResponseEntity.badRequest().body("INSERT_FAIL");
    }

    //게시물 수정은 제목 ,내용만 수정 가능해야 합니다.
    // 게시물 수정
    @PatchMapping("/{postNo}")
    public ResponseEntity<?> modify(@PathVariable Long postNo,@RequestBody PostModifyDTO modifyDTO) {
        log.info("/posts/{} PATCH request", postNo);

        // 수정 전 데이터 조회하기
        PostEntity entity = postRepository.findOne(postNo);
        // 수정 진행
        String modTitle = modifyDTO.getTitle();
        String modContent = modifyDTO.getContent();

        if (modTitle != null) entity.setTitle(modTitle);
        if (modContent != null) entity.setContent(modContent);
        entity.setModifyDate(LocalDateTime.now());

        boolean flag = postRepository.save(entity);
        return flag
                ? ResponseEntity.ok().body("MODIFY-SUCCESS")
                : ResponseEntity.badRequest().body("MODIFY-FAIL")
                ;

    }


    // 게시물 삭제
    @DeleteMapping("/{postNo}")
    public ResponseEntity<?> remove(@PathVariable Long postNo) {
        log.info("/posts/{} DELETE request", postNo);
        boolean flag = postRepository.delete(postNo);
        return flag
                ? ResponseEntity.ok().body("DELETE-SUCCESS")
                : ResponseEntity.badRequest().body("DELETE-FAIL")
                ;
    }

}
