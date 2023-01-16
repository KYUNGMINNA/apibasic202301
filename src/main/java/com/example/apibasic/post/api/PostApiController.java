package com.example.apibasic.post.api;


import com.example.apibasic.post.dto.*;
import com.example.apibasic.post.entity.PostEntity;
import com.example.apibasic.post.repository.PostRepository;
import com.example.apibasic.post.service.PostService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
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


    //PostService에 의존 관계
    private final PostService postService;


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


        try {
            //Service에서 isEMpty() 있으면 throw new 하는 코드 있어
            //예외처리해야함
            PostListResponseDTO listResponseDTO = postService.getList();

            return  ResponseEntity
                    .ok()
                    .body(listResponseDTO);
        } catch (Exception e) { //실무에서는 Catch가 다양하게 있을 수 있다.
             return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    //게시물 개별 조회시에는 클라이언트에게  추가로 수정시간 정보를 제공하세요

    //게시물 개별 조회
    @GetMapping("/{postNo}")
    public ResponseEntity<?> detail(
           //@PathVariable("postNo") Long No){
            @PathVariable Long postNo){
        log.info("/posts/{} GET request",postNo);

        try {
            PostDetailResponseDTO dto = postService.getDetail(postNo);

            return ResponseEntity
                    .ok()
                    .body(dto);
        } catch (Exception e) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }


    // 게시물 등록
    @Parameters({
            @Parameter(name = "작성자", description = "게시물 작성자를 입력", example = "김철수")
            ,@Parameter(name = "내용", description = "글 내용을 입력", example = "하하하호호호~~")
    }) // 파라미터에 넣을 수 있는 것 설명
    @PostMapping
    public ResponseEntity<?> create(
            @Validated @RequestBody PostCreateDTO createDTO
            , BindingResult result // 검증 에러 정보를 갖고 있는 객체
    ) {
        if (result.hasErrors()) { // 검증에러가 발생할 시 true 리턴
            List<FieldError> fieldErrors = result.getFieldErrors();
            fieldErrors.forEach(err -> {
                log.warn("invalidated client data - {}", err.toString());
            });
            return ResponseEntity
                    .badRequest()
                    .body(fieldErrors);
        }

        log.info("/posts POST request");
        log.info("게시물 정보: {}", createDTO);

        return postService.insert(createDTO)
                ? ResponseEntity.ok().body("INSERT-SUCCESS")
                : ResponseEntity.badRequest().body("INSERT-FAIL")
                ;
    }

    //게시물 수정은 제목 ,내용만 수정 가능해야 합니다.
    // 게시물 수정
    @PatchMapping("/{postNo}")
    public ResponseEntity<?> modify(
            @PathVariable Long postNo
            , @RequestBody PostModifyDTO modifyDTO
    ) {
        log.info("/posts/{} PATCH request", postNo);
        log.info("수정할 정보 : {}", modifyDTO);

        return postService.update(postNo, modifyDTO)
                ? ResponseEntity.ok().body("MODIFY-SUCCESS")
                : ResponseEntity.badRequest().body("MODIFY-FAIL")
                ;
    }



    // 게시물 삭제
    @DeleteMapping("/{postNo}")
    public ResponseEntity<?> remove(@PathVariable Long postNo) {
        log.info("/posts/{} DELETE request", postNo);

        return postService.delete(postNo)
                ? ResponseEntity.ok().body("DELETE-SUCCESS")
                : ResponseEntity.badRequest().body("DELETE-FAIL")
                ;
    }


}
