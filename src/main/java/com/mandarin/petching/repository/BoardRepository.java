package com.mandarin.petching.repository;

import com.mandarin.petching.domain.Board;
import com.mandarin.petching.domain.BoardType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByTitle(String title);
    List<Board> findByTitleOrContent(String title, String content);
    Page<Board> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);//제목이나 내용으로 검색


    //김귀영 추가
    Page<Board> findByBoardTypeOrBoardTypeOrBoardType(BoardType keyword1, BoardType keyword2, BoardType keyword3, Pageable pageable);
//    Page<Board> findByContentContaining(String content, Pageable pageable);//by는 where Containing는 like 검색
//    Page<Board> findByTitleContaining(String title, Pageable pageable);
//    Page<Board> findByBoardTypeContaining(String keyword, Page pageable);
    Page<Board> deleteByBoardTypeOrBoardTypeOrBoardType(BoardType keyword1, BoardType keyword2, BoardType keyword3, Pageable pageable);


}

