package com.mandarin.petching.domain;


import lombok.*;

import org.hibernate.annotations.CreationTimestamp;


import java.time.LocalDateTime;
import javax.persistence.*;





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

    @Column(columnDefinition = "integer default 0", nullable = false)
    private Integer hits;

    @Column(name = "reg_date", updatable = false)
    @CreationTimestamp
    private LocalDateTime regDate;
    @Column(length = 20)
    private String title;

    @Lob
    private String content;

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

