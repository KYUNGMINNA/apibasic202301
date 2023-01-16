package com.example.apibasic.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//설정 파일
@Configuration  //스프링 컨테이너 에게 설정파일이라고 알림
public class SwaggerConfig {

    // <bean id=groupedOpenApi class=org.springdoc.core.GroupedOpenApi />
    @Bean
    public GroupedOpenApi groupedOpenApi(){
        return GroupedOpenApi
                .builder()
                .group("lalala project")
                .pathsToMatch("/posts/**","/users/**")
                .build();
    }

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(
                  new Info()
                          .title("my api~~")
                          .description("내 api 명세서입니다")
                          .version("v1.0.0")
                );
    }
}
