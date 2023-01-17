package com.example.apibasic.post.service;

import com.example.apibasic.post.dto.*;
import com.example.apibasic.post.entity.PostEntity;
import com.example.apibasic.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Slf4j
//스프링빈 등록 = 객체의 라이프사이클을 스프링컨테이너에게 위임
//@Component가 기본인데 ,Srping MVC에는 각각의 Layer에 맞는 아노테이션이 있음
@Service
public class PostService {

    private final PostRepository postRepository;

    //목록 조회 중간 처리
    public PostListResponseDTO getList(PageRequestDTO pageRequestDTO) {

        //Pageable 로 받아도 되고 , PageReuest로 받아오 된다 왜냐면 Pagerequest는 AbstractPageRequest를 상속받고 ,AbstractPageRequest는  implements Pageable
        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSizePerPage(),
                Sort.Direction.DESC,
                "createDate"
        );

        final Page<PostEntity> pageData=postRepository.findAll(pageable); // Page는 페이지 정보 뿐만 아니라 , 객체 정보도 갖고 있음
        List<PostEntity> list = pageData.getContent();

        if (list.isEmpty()) {
            throw new RuntimeException("조회 결고가 없어용~");
        }

        //엔터티 리스트를 DTO리스트로 변환해서 클라이언트에 응답
        List<PostResponseDTO> responseDTOList = list.stream()
                                                //.map(et->new PostResponseDTO(et))
                                                .map(PostResponseDTO::new)
                                                .collect(toList());

        PostListResponseDTO listResponseDTO = PostListResponseDTO.builder()
                                                .count(responseDTOList.size())
                                                .pageInfo(new PageResponseDTO<PostEntity>(pageData))
                                                .posts(responseDTOList)
                                                .build();


        return listResponseDTO;
    }

    // 개별 조회 중간처리
    public PostDetailResponseDTO getDetail(Long postNo) {
        PostEntity post = postRepository
                        .findById(postNo)
                        .orElseThrow(()->new RuntimeException(
                                postNo + "번 게시물이 존재하지 않음!!"
                        ));

        //Optional :후속 행동들으 처리할 수 있게 도와주는 유틸 메서드를 갖고 있음
        // orElse(널이면 할 행동) , orElseThrow() :널이면 발생시키겠따.
        //if (postEntity == null) throw new RuntimeException(postNo + "번 게시물이 존재하지 않음!!");

        // 엔터티를 DTO로 변환
        return new PostDetailResponseDTO(post);
    }

    //매개 변수를 final로 선언해서  데이터가
    //바뀌는것을 방지 ( DB에 안정적으로 넘기기위해)
    // 등록 중간처리
    public PostDetailResponseDTO insert(final PostCreateDTO createDTO)
        //throws IllegalArgumentException, OptimisticLockingFailureException
            throws  RuntimeException
    {
        // dto를 entity변환 작업
        final PostEntity entity = createDTO.toEntity();

        //메서드 클릭해 봤을 때 던지기 있으면 에러 발생 가능성 있음
        PostEntity savedPost = postRepository.save(entity);
        //저장딘 객체를 DTO로 변환해서 반환



        return new PostDetailResponseDTO(savedPost);
    }

    // 수정 중간 처리
    public PostDetailResponseDTO update(final Long postNo, final PostModifyDTO modifyDTO)
    throws RuntimeException{

        // 수정 전 데이터 조회하기
       final PostEntity entity = postRepository
                        .findById(postNo)
                        .orElseThrow(
                                ()->new RuntimeException("수정 전 데이터가 존재하지 않습니다.")
                        );

        // 수정 진행
        String modTitle = modifyDTO.getTitle();
        String modContent = modifyDTO.getContent();

        if (modTitle != null) entity.setTitle(modTitle);
        if (modContent != null) entity.setContent(modContent);

        PostEntity modifiedPost = postRepository.save(entity);

        return new PostDetailResponseDTO(modifiedPost);
    }
    // 삭제 중간처리
    public void delete(final Long postNo)
            throws RuntimeException {
        //delete () : 엔터티가 필요

        //deleteById() : Id가 필요

         postRepository.deleteById(postNo);
    }



}
