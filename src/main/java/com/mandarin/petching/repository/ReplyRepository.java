package com.mandarin.petching.repository;

import com.mandarin.petching.domain.Board;
import com.mandarin.petching.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
}
