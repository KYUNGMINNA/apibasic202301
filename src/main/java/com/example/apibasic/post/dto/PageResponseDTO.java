package com.example.apibasic.post.dto;

// 클라이언트에게 응답할 페이지 정보
/*
    pageInfo : {
        "startPage" : 1,
        "endPage" : 10,
        "currentPage" : 3,
        "prev" : false,
        "next" : true,
        "totalCount": 500
    }
 */

import com.example.apibasic.post.entity.PostEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;

@ToString @Getter @Setter
public class PageResponseDTO<T> {
    private int startPage;
    private int endPage;
    private int  currentPage;
    private boolean prev;
    private boolean next;
    private int totalCount;

    //페이지 개수
    private static final int PAGE_COUNT=10;



    //게시글 전용으로 사용하는것이 아니라서 범용적으로 쓰기 위해 제너릭 사용
    public PageResponseDTO(Page<T> pageData) { //pageData는 DB에서 넘어온데이터
        this.totalCount= (int) pageData.getTotalElements();
        this.currentPage=pageData.getPageable().getPageNumber()+1; // PageRequest에서 -1을 했기 때문임!!
        this.endPage= (int) (Math.ceil((double) currentPage/PAGE_COUNT)*PAGE_COUNT); // 정수/정수는 정수라서 , 강제로 dobuble타입으로 형변환
        this.startPage=endPage-PAGE_COUNT+1;

        //페이지 마지막 구간에서 endPage 값 보정
        //실제 끝 페이지 숫자를 구함
        //int realEnd= (int) Math.ceil((double) totalCount/pageData.getSize());
        int realEnd=pageData.getTotalPages();

        //언제 보정해야 돼? 마지막 구간에서만
        if (realEnd<endPage) this.endPage=realEnd;

        this.prev=startPage >1;
        this.next=endPage<realEnd;

    }
}
