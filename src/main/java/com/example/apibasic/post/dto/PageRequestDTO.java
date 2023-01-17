package com.example.apibasic.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

//클라이언트가 보낸 페이지 정보를 가진 객체
@Getter
@AllArgsConstructor
@Builder @ToString
public class PageRequestDTO {

    //스프링은 파라미터로 들어온 DTO같은 값들을 사용할 때
    // 기본생성자 + setter를 이용하여 초기화 함


    private int page; //요청한 페이지 번호
    private int sizePerPage; //한 페이지에 보여줄 데이터 수

    //초기 요청시에 사용할 데이터  :게시판 클릭하자마자 보여지는 첫 페이지
    public PageRequestDTO() {
        this.page=1;
        this.sizePerPage=10;
    }

    //주소창에 이상한 값 들어오는거 방지
    public void setPage(int page) {
        if(page<1 || page>Integer.MAX_VALUE){
            this.page=1;
            return;
        }
        this.page = page;
    }

    //주소창에 이상한 값 들어오는거 방지
    public void setSizePerPage(int sizePerPage) {
        if (sizePerPage<10 || sizePerPage>100){
            this.sizePerPage=10;
            return;
        }
        this.sizePerPage = sizePerPage;
    }
}
