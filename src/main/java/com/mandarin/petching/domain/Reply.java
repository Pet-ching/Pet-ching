package com.mandarin.petching.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Reply {

    @Id
    @Column(name="reply_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="board_id", referencedColumnName = "board_id")
    private Board board;

    private String reContent;

    @CreatedDate
    private LocalDateTime reRegDate;

    @ManyToOne
    @JoinColumn(name="user_id")
    private Member member;
}
