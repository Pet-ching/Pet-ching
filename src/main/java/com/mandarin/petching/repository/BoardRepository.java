package com.mandarin.petching.repository;

import com.mandarin.petching.domain.Board;
import com.mandarin.petching.domain.BoardType;
import com.mandarin.petching.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByTitle(String title);
    List<Board> findByTitleOrContent(String title, String content);
    Page<Board> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);//제목이나 내용으로 검색


    //김귀영 추가
    //qna 전부 검색
    Page<Board> findByBoardTypeBetweenAndMemberLike (BoardType start, BoardType last,Member member, Pageable pageable);

    //select * from board where board_type between 'QnA문의1' and 'QnA문의3' and title like'%김%';
    // 제목으로 검색
    Page<Board> findByBoardTypeBetweenAndTitleContainingAndMemberLike (BoardType startBT, BoardType lastBT, String keyword,Member member, Pageable pageable);

    // 내용으로 검색
    Page<Board> findByBoardTypeBetweenAndContentContainingAndMemberLike (BoardType startBT, BoardType lastBT, String keyword,Member member, Pageable pageable);

    // 제목 + 내용으로 검색
    Page<Board> findByBoardTypeBetweenAndTitleContainingOrContentContainingAndMemberLike (BoardType start, BoardType last,String title, String content, Member member, Pageable pageable);

    //삭제
    List<Board> deleteByBoardTypeBetweenAndMemberLike(BoardType start, BoardType last, Member member);





}

