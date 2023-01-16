package com.example.apibasic.post.repository;

//일반적으로는 인터페이스로 작성

import com.example.apibasic.post.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.*;

// 게시물 데이터를 CRUD(생성 ,조회 ,수정,삭제)
@Repository //이 클래스로 만든 객체는 스프링이 라이프사이클을 관리좀 해줘라(맡기는것)
public interface PostRepository extends JpaRepository<PostEntity,Long> {


}
