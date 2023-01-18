package com.example.apibasic.post.entity;

import lombok.*;

import javax.persistence.*;

@Setter @Getter @ToString
@NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(of = "id")
@Builder
@Entity
@Table(name = "hash_tag") //DB의 테이블명은 단수형이 관례
public class HashTagEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tagName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_no") // joinColum -컬럼명 바꾸기
    private PostEntity post;
}
