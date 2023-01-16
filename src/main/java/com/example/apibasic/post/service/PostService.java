package com.example.apibasic.post.service;

import com.example.apibasic.post.dto.*;
import com.example.apibasic.post.entity.PostEntity;
import com.example.apibasic.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Slf4j
//스프링빈 등록 = 객체의 라이프사이클을 스프링컨테이너에게 위임
//@Component가 기본인데 ,Srping MVC에는 각각의 Layer에 맞는 아노테이션이 있음
@Service
public class PostService {

    private final PostRepository postRepository;

    //목록 조회 중간 처리
    public PostListResponseDTO getList() {
        List<PostEntity> list = postRepository.findAll();

        if (list.isEmpty()) {
            throw new RuntimeException("조회 결고가 없어용~");
        }

        //엔터티 리스트를 DTO리스트로 변환해서 클라이언트에 응답
        List<PostResponseDTO> responseDTOList = list.stream()
                //.map(et->new PostResponseDTO(et))
                .map(PostResponseDTO::new).collect(toList());

        PostListResponseDTO listResponseDTO = PostListResponseDTO.builder().count(responseDTOList.size()).posts(responseDTOList).build();

        return listResponseDTO;
    }

    // 개별 조회 중간처리
    public PostDetailResponseDTO getDetail(Long postNo) {
        Optional<PostEntity> post = postRepository.findById(postNo);
        PostEntity postEntity = post.get();

        if (postEntity == null) throw new RuntimeException(postNo + "번 게시물이 존재하지 않음!!");

        // 엔터티를 DTO로 변환
        return new PostDetailResponseDTO(postEntity);
    }

    //매개 변수를 final로 선언해서  데이터가
    //바뀌는것을 방지 ( DB에 안정적으로 넘기기위해)
    // 등록 중간처리
    public boolean insert(final PostCreateDTO createDTO) {
        // dto를 entity변환 작업
        final PostEntity entity = createDTO.toEntity();
        postRepository.save(entity);
        return true;
    }

    // 수정 중간 처리
    public boolean update(final Long postNo, final PostModifyDTO modifyDTO) {
        // 수정 전 데이터 조회하기
        Optional<PostEntity> post = postRepository.findById(postNo);
        PostEntity postEntity = post.get();
        // 수정 진행
        String modTitle = modifyDTO.getTitle();
        String modContent = modifyDTO.getContent();

        if (modTitle != null) postEntity.setTitle(modTitle);
        if (modContent != null) postEntity.setContent(modContent);
        postEntity.setModifyDate(LocalDateTime.now());
        postRepository.save(postEntity);
        return true;
    }
    // 삭제 중간처리
    public boolean delete(final Long postNo) {

         postRepository.deleteById(postNo);
        return true;
    }



}
