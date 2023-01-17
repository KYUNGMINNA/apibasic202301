package com.example.apibasic.post.repository;

//일반적으로는 인터페이스로 작성

import com.example.apibasic.post.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.*;
//JpaRepository 상속 받으면 ,해당 인터페이스를 프록시 객체로 만들어  주고
//@Reposotry까지 붙여 준다.
public interface PostRepository extends JpaRepository<PostEntity,Long> {


}
