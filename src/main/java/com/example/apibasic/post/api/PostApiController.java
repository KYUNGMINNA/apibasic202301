package com.example.apibasic.post.api;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
public class PostApiController {

    //게시물 목록 조회
    @GetMapping   //RequestMapping이 있어 비워놔도 된다.
    public ResponseEntity<?> list(){
        log.info("/posts GET request");
        return  null;
    }

    //게시물 개별 조회
    @GetMapping("/{postNo}")
    public ResponseEntity<?> detail(
           //@PathVariable("postNo") Long No){
            @PathVariable Long postNo){
        log.info("/posts/{} GET request",postNo);
        return  null;
    }
    // 게시물 등록
    @PostMapping
    public ResponseEntity<?> create() {
        log.info("/posts POST request");
        return null;
    }

    // 게시물 수정
    @PatchMapping("/{postNo}")
    public ResponseEntity<?> modify(@PathVariable Long postNo) {
        log.info("/posts/{} PATCH request", postNo);
        return null;
    }
    // 게시물 삭제
    @DeleteMapping("/{postNo}")
    public ResponseEntity<?> remove(@PathVariable Long postNo) {
        log.info("/posts/{} DELETE request", postNo);
        return null;
    }

}
