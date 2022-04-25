package com.mandarin.petching.repository;

import com.mandarin.petching.domain.Board;
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

    Page<Board> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);

    @Modifying
    @Query("update Board p set p.hits = p.hits + 1 where p.id = :id")
    int  updateHits(Long id);



}

