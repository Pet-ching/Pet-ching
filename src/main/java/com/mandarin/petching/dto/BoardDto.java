package com.mandarin.petching.dto;

import com.mandarin.petching.domain.*;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.*;

@NoArgsConstructor
public class BoardDto {
    private Long id;


    private Member member;

    private Integer hits;

    private LocalDateTime regDate;

    private String title;

    private String content;

    private BoardType boardType;

    private AnswerType answerType;

//    private List<Reply> replies;
//
//    public BoardDto(Board board) {
//        this.id = board.getId();
//        this.member = board.getMember();
//        this.hits = board.getHits();
//        this.regDate = board.getRegDate();
//        this.title = board.getTitle();
//        this.content = board.getContent();
//        this.boardType = board.getBoardType();
//        this.answerType = board.getAnswerType();
////        this.replies = null;
//    }

    public void setBoardDto(Board board) {
        this.id = board.getId();
        this.member = board.getMember();
        this.hits = board.getHits();
        this.regDate = board.getRegDate();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.boardType = board.getBoardType();
        this.answerType = board.getAnswerType();
//        this.replies = null;
    }

    public Map<String, Object> toJson(Board board)
    {
//        board = setBoardDto(board);
        Map<String, Object> json =  new HashMap<String, Object>();
        json.put("id", board.getId());
        json.put("member", board.getMember());
        json.put("hits", board.getHits());
        json.put("regDate", board.getRegDate());
        json.put("title", board.getTitle());
        json.put("content", board.getContent());
        json.put("boardType",board.getBoardType());
        json.put("answerType",board.getAnswerType());

        return json;

    }

}
