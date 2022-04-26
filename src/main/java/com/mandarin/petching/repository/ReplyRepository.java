package com.mandarin.petching.repository;

import com.mandarin.petching.domain.Board;
import com.mandarin.petching.domain.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {

    Reply findByBoard(Board board);
    void deleteByBoard(Board board);
}
