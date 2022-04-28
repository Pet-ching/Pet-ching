package com.mandarin.petching.repository;

import com.mandarin.petching.domain.Board;
import com.mandarin.petching.domain.BoardType;
import com.mandarin.petching.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByTitleOrContent(String title, String content);
    Page<Board> findByBoardTypeOrderByIdDesc(BoardType boardType, Pageable pageable);


    //김귀영 추가

    //qna 전부 검색
//    List<Board> findByBoardTypeBetween(BoardType start, BoardType last);
    List<Board> findByBoardTypeBetweenAndMemberLike (BoardType start, BoardType last,Member member);

    Page<Board> findByBoardTypeBetween(BoardType start, BoardType last, Pageable pageable);
    Page<Board> findByBoardTypeBetweenAndMemberLike (BoardType start, BoardType last,Member member, Pageable pageable);

    // 제목으로 검색
    Page<Board> findByBoardTypeBetweenAndTitleContaining (BoardType startBT, BoardType lastBT, String keyword, Pageable pageable);
    Page<Board> findByBoardTypeBetweenAndTitleContainingAndMemberLike (BoardType startBT, BoardType lastBT, String keyword,Member member, Pageable pageable);

    // 내용으로 검색
    Page<Board> findByBoardTypeBetweenAndContentContaining (BoardType startBT, BoardType lastBT, String keyword, Pageable pageable);
    Page<Board> findByBoardTypeBetweenAndContentContainingAndMemberLike (BoardType startBT, BoardType lastBT, String keyword,Member member, Pageable pageable);

    // 제목 + 내용으로 검색
    Page<Board> findByBoardTypeBetweenAndTitleContainingOrContentContaining(BoardType start, BoardType last,String title, String content, Pageable pageable);
    Page<Board> findByBoardTypeBetweenAndTitleContainingOrContentContainingAndMemberLike (BoardType start, BoardType last,String title, String content, Member member, Pageable pageable);

    //삭제
    List<Board> deleteByBoardTypeBetween(BoardType start, BoardType last);
//    List<Board> deleteByBoardTypeBetweenAndMemberLike(BoardType start, BoardType last, @Param("member_id")Member member);//@Pram("쿼리에서 사용할 변수명") 이거 써야 console에 오류 잘 보임

    List<Board> deleteByBoardTypeBetweenAndMemberLike(BoardType start, BoardType last, Member member);



    @Modifying
    @Query("update Board p set p.hits = p.hits + 1 where p.id = :id")
    int  updateHits(@Param("id") Long id);
}

