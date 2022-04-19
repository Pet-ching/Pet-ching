package com.mandarin.petching.domain;

import lombok.Getter;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import org.springframework.data.annotation.CreatedDate;


@Getter
@Entity
public class Board {
    @Id
    @Column(
            name = "board_id"
    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @ManyToOne
    private Member member;
    private Integer hits;
    @CreatedDate
    private LocalDateTime regDate;
    @Column(
            length = 20
    )
    private String title;
    @Lob
    private String content;
    @Enumerated(EnumType.STRING)
    private BoardType boardType;
    @Enumerated(EnumType.STRING)
    private AnswerType answerType;

    public Board() {
    }

    public Board(Member member, Integer hits, LocalDateTime regDate, String title, String content, BoardType boardType, AnswerType answerType) {
        this.member = member;
        this.hits = hits;
        this.regDate = regDate;
        this.title = title;
        this.content = content;
        this.boardType = boardType;
        this.answerType = answerType;
    }

    public static Board.BoardBuilder builder() {
        return new Board.BoardBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public Member getMember() {
        return this.member;
    }

    public Integer getHits() {
        return this.hits;
    }

    public LocalDateTime getRegDate() {
        return this.regDate;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public BoardType getBoardType() {
        return this.boardType;
    }

    public AnswerType getAnswerType() {
        return this.answerType;
    }

    public static class BoardBuilder {
        private Member member;
        private Integer hits;
        private LocalDateTime regDate;
        private String title;
        private String content;
        private BoardType boardType;
        private AnswerType answerType;

        BoardBuilder() {
        }

        public Board.BoardBuilder member(final Member member) {
            this.member = member;
            return this;
        }

        public Board.BoardBuilder hits(final Integer hits) {
            this.hits = hits;
            return this;
        }

        public Board.BoardBuilder regDate(final LocalDateTime regDate) {
            this.regDate = regDate;
            return this;
        }

        public Board.BoardBuilder title(final String title) {
            this.title = title;
            return this;
        }

        public Board.BoardBuilder content(final String content) {
            this.content = content;
            return this;
        }

        public Board.BoardBuilder boardType(final BoardType boardType) {
            this.boardType = boardType;
            return this;
        }

        public Board.BoardBuilder answerType(final AnswerType answerType) {
            this.answerType = answerType;
            return this;
        }

        public Board build() {
            return new Board(this.member, this.hits, this.regDate, this.title, this.content, this.boardType, this.answerType);
        }

        public String toString() {
            return "Board.BoardBuilder(member=" + this.member + ", hits=" + this.hits + ", regDate=" + this.regDate + ", title=" + this.title + ", content=" + this.content + ", boardType=" + this.boardType + ", answerType=" + this.answerType + ")";
        }
    }
}