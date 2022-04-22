package com.mandarin.petching.domain;


import lombok.*;

import org.springframework.data.annotation.CreatedDate;


import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;




@Getter
@Setter
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Board {
    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private Integer hits;

    @CreatedDate
    private LocalDateTime regDate;
    @Column(
            length = 20
    )

    @NotBlank(message = "제목을 입력해 주세요!")
    private String title;

    @Lob
    @NotBlank(message = "제목을 입력해 주세요!")
    private String content;

    @NotNull(message = "보드 타입을 선택해 주세요!")
    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    @Enumerated(EnumType.STRING)
    private AnswerType answerType;

    @Builder
    public Board(String title, String content, BoardType boardType, LocalDateTime regDate, Member member, AnswerType answerType) {
        this.title = title;
        this.content = content;
        this.boardType = boardType;
        this.regDate = regDate;
        this.member = member;
        this.answerType = answerType;
    }

}

