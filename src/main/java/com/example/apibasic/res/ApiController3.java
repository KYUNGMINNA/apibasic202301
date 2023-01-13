package com.example.apibasic.res;

import com.example.apibasic.req.ApiController2;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.List;

//@Controller  -- 일반적으로 Controller의 응답  헤더의 content-type :text/html이다.
//@ResponseBody // view 탬플릿을 사용하지 않고 ,JSON으로 데이터를 넘기겠다라는 의미

@RestController
@Slf4j
public class ApiController3 {

    @GetMapping("/res1")
    public String res1(){
        log.info("/res1 GET!!!");
        return "hello";
    }

    //produces = "application/json"가 기본적으로 적용되어 있음 -그래서 생략해도됨
    //consumes = "application/json"가 기본적으로 적용되어 있음
    @GetMapping(value = "/res2",consumes = "application/json",produces = "application/json")
    public List<String> res2(){
        log.info("/res2 GET!!!");
        return List.of("짜장면","볶음밥","탕수육");
    }

    @GetMapping(value = "/res3",produces = "application/json")
    public ApiController2.OrderInfo res3(){
        log.info("/res3 GET!!!");

        ApiController2.OrderInfo orderInfo = new ApiController2.OrderInfo();
        orderInfo.setOrderNo(12L);
        orderInfo.setGoodsName("세탁기");
        orderInfo.setGoodsAmout(3);

        return orderInfo;
    }

    //사원 목록 정보
    @GetMapping("/employees")
    public List<Employee> empList(){

        return List.of(
        new Employee("홍길동","영업부",LocalDate.of(2019,12,11))
        ,new Employee("박영희","구매부",LocalDate.of(2010,3,5))
        ,new Employee("김수호","개발부",LocalDate.of(2022,4,23))
        );
    }



    @Getter @Setter @ToString
    @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
    @Builder
    public static class Employee{
        private String empName;
        private String deptName;
        private LocalDate hireDate;

    }


    //응답시에 응답헤더정보와 응답사태코드를 조작하려면 Responseentity객체 사용
    @GetMapping("/res4")
    public ResponseEntity<?> res4(String nick){
        if(nick ==null || nick.equals("")){
            return ResponseEntity.badRequest().build();
        }





        Employee employee = Employee.builder()
                .empName(nick)
                .deptName("영업부")
                .hireDate(LocalDate.of(2019, 3, 31))
                .build();

        //응답 헤더 생성  : 직접  만들 수도 있고, 기존의 코드를 수정할 수 있다.
        HttpHeaders headers=new HttpHeaders();
        headers.set("department","business");
        headers.set("blah-blah","hahahoho");


        return ResponseEntity
                //.status(HttpStatus.OK)
                //.status(200)
                .ok()
                .headers(headers)
                .body(employee);
        //header와 body는 선택사항 임 !! 없으면 안넣어도 됨


        //return ResponseEntity.ok(nick+"님 잘했어요~");
    }


}
