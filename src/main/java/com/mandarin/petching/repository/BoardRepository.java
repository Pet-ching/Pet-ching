package com.mandarin.petching.repository;

import com.mandarin.petching.domain.Board;
import com.mandarin.petching.domain.BoardType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByTitle(String title);
    List<Board> findByTitleOrContent(String title, String content);
    Page<Board> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);//제목이나 내용으로 검색


    //김귀영 추가
    //검색
    Page<Board> findByBoardTypeOrBoardTypeOrBoardType(BoardType keyword1, BoardType keyword2, BoardType keyword3, Pageable pageable);

    //select * from board where board_type between 'QnA문의1' and 'QnA문의3' and title like'%김%';
    // 제목으로 검색
    Page<Board> findByBoardTypeBetweenAndTitleContaining (BoardType startBT, BoardType lastBT, String keyword, Pageable pageable);

    // 내용으로 검색
    Page<Board> findByBoardTypeBetweenAndContentContaining (BoardType startBT, BoardType lastBT, String keyword, Pageable pageable);

    // 제목 + 내용으로 검색
    Page<Board> findByBoardTypeBetweenAndTitleContainingOrContentContaining (BoardType start, BoardType last,String title, String content, Pageable pageable);


    //삭제
    List<Board> deleteByBoardTypeOrBoardTypeOrBoardType(BoardType keyword1, BoardType keyword2, BoardType keyword3);






}

