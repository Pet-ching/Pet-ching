package com.mandarin.petching.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Board_test {

    /*기본키ID*/
    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*글작성자*/
    @ManyToOne
    private Member member;

    /*조회수*/
    private Integer hits;

    /*글작성날짜*/
    @CreatedDate
    private LocalDateTime regDate;

    /*제목*/
    @Column(length = 20)
    private String title;

    /*내용*/
    @Lob
    private String content;

    /*카테고리*/
    @Enumerated(EnumType.STRING)
    private BoardType boardType;
}
