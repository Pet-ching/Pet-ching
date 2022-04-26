package com.mandarin.petching.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Reply {

    @Id
    @Column(name="reply_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="board_id")
    private Board board;

    @NotBlank
    private String reContent;

    @CreatedDate
    private LocalDateTime reRegDate;

    @ManyToOne
    @JoinColumn(name="user_id")
    private Member member;
}
